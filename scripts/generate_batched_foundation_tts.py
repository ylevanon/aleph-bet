#!/usr/bin/env python3
"""Generate alphabet/niqqud clips from four quota-efficient TTS batches per voice."""

from __future__ import annotations

import argparse
import json
import math
import os
import struct
import sys
import wave
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

import generate_tts


ROOT = Path(__file__).resolve().parents[1]
AUDIO = ROOT / "asset-pack" / "audio"
MODEL = "gemini-2.5-pro-preview-tts"
GROUPS = (
    ("alphabet_names", "alphabet", "letter_name"),
    ("alphabet_sounds", "alphabet", "sound"),
    ("niqqud_names", "niqqud", "mark_name"),
    ("niqqud_sounds", "niqqud", "sound"),
)


def load_key(env_name: str, env_file: Path | None) -> str:
    value = os.environ.get(env_name)
    if value:
        return value
    if env_file:
        for raw_line in env_file.expanduser().read_text(encoding="utf-8").splitlines():
            line = raw_line.strip()
            if not line or line.startswith("#") or "=" not in line:
                continue
            name, candidate = line.split("=", 1)
            if name.strip() == env_name:
                return candidate.strip().strip('"').strip("'")
    raise SystemExit(f"{env_name} is not set")


def batch_payload(voice: str, jobs: list[generate_tts.Job], label: str) -> dict[str, Any]:
    lines = "\n".join(job.transcript for job in jobs)
    prompt = (
        f"Create exactly {len(jobs)} isolated Modern Israeli Hebrew {label.replace('_', ' ')} "
        "recordings in one audio stream. Use a native contemporary Israeli accent, warm neutral "
        "teaching tone, and crisp natural articulation. Speak ONLY the Hebrew lines inside the "
        "items block, once each and in their exact order. Do not speak numbers, labels, tags, an "
        "introduction, or an ending. Leave exactly two seconds of complete silence between every "
        f"line.\n\n<items>\n{lines}\n</items>"
    )
    return {
        "contents": [{"parts": [{"text": prompt}]}],
        "generationConfig": {
            "responseModalities": ["AUDIO"],
            "speechConfig": {
                "voiceConfig": {"prebuiltVoiceConfig": {"voiceName": voice}}
            },
        },
    }


def silence_gaps(path: Path, expected: int) -> tuple[list[tuple[int, int]], int]:
    with wave.open(str(path), "rb") as reader:
        if reader.getnchannels() != 1 or reader.getsampwidth() != 2:
            raise RuntimeError("batch WAV must be 16-bit mono")
        rate = reader.getframerate()
        raw = reader.readframes(reader.getnframes())
    samples = struct.unpack(f"<{len(raw) // 2}h", raw)
    window = max(1, int(rate * 0.02))
    rms: list[float] = []
    for start in range(0, len(samples), window):
        chunk = samples[start:start + window]
        rms.append(math.sqrt(sum(sample * sample for sample in chunk) / max(1, len(chunk))))
    peak = max(rms, default=0)

    candidates: list[tuple[int, int]] = []
    for threshold_ratio, minimum_seconds in ((0.018, 0.75), (0.03, 0.55), (0.05, 0.35)):
        threshold = max(120.0, peak * threshold_ratio)
        minimum_windows = max(1, int(minimum_seconds / 0.02))
        found: list[tuple[int, int]] = []
        start: int | None = None
        for index, value in enumerate(rms + [threshold + 1]):
            if value <= threshold and start is None:
                start = index
            elif value > threshold and start is not None:
                if index - start >= minimum_windows:
                    sample_start = start * window
                    sample_end = min(index * window, len(samples))
                    if sample_start > rate * 0.1 and sample_end < len(samples) - rate * 0.1:
                        found.append((sample_start, sample_end))
                start = None
        if len(found) >= expected - 1:
            candidates = sorted(
                sorted(found, key=lambda gap: gap[1] - gap[0], reverse=True)[:expected - 1]
            )
            break
    if len(candidates) != expected - 1:
        raise RuntimeError(f"detected {len(candidates) + 1}/{expected} spoken segments in {path.name}")
    return candidates, rate


def split_batch(
    master: Path,
    jobs: list[generate_tts.Job],
    bitrate: str,
    index_items: dict[str, Any],
) -> None:
    gaps, rate = silence_gaps(master, len(jobs))
    boundaries = [0] + [(start + end) // 2 for start, end in gaps]
    with wave.open(str(master), "rb") as reader:
        total_frames = reader.getnframes()
        frames = reader.readframes(total_frames)
    boundaries.append(total_frames)

    segment_dir = master.parent / f"{master.stem}-segments"
    segment_dir.mkdir(parents=True, exist_ok=True)
    for job, start, end in zip(jobs, boundaries, boundaries[1:], strict=True):
        duration = (end - start) / rate
        if not 0.15 <= duration <= 12.0:
            raise RuntimeError(f"implausible {duration:.2f}s segment for {job.id}")
        segment_master = segment_dir / f"{job.id.replace('.', '_')}.wav"
        with wave.open(str(segment_master), "wb") as output:
            output.setnchannels(1)
            output.setsampwidth(2)
            output.setframerate(rate)
            output.writeframes(frames[start * 2:end * 2])
        output_path = AUDIO / job.relative_output
        generate_tts.encode_mp3(segment_master, output_path, bitrate)
        details = generate_tts.probe(output_path)
        index_items[job.id] = {
            "path": job.relative_output,
            "transcript": job.transcript,
            "profile": job.profile,
            "voice": job.voice,
            "model": MODEL,
            "generatedAt": datetime.now(timezone.utc).isoformat(),
            **details,
        }
        segment_master.unlink(missing_ok=True)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--voice", required=True, choices=("Sulafat", "Iapetus"))
    parser.add_argument("--worker-tag", required=True)
    parser.add_argument("--env-file", type=Path)
    parser.add_argument("--api-key-env", default="GEMINI_API_KEY")
    parser.add_argument("--retries", type=int, default=2)
    parser.add_argument("--force-batches", action="store_true")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    api_key = load_key(args.api_key_env, args.env_file)
    config = generate_tts.load_json(generate_tts.CONFIG_PATH)
    config = {**config, "model": MODEL}
    jobs = generate_tts.select_jobs(generate_tts.build_jobs(config, args.voice), "foundation", None, None)
    worker_dir = AUDIO / "workers"
    index_path = worker_dir / f"{args.worker_tag}-index.json"
    index = {"schemaVersion": 1, "items": {}}
    batch_dir = AUDIO / "batches" / "gemini-2.5-pro-preview-tts" / args.voice.lower()
    batch_dir.mkdir(parents=True, exist_ok=True)

    for position, (label, category, profile) in enumerate(GROUPS, 1):
        group_jobs = [job for job in jobs if job.category == category and job.profile == profile]
        master = batch_dir / f"{label}.wav"
        if not master.exists() or args.force_batches:
            print(f"[{position}/4] Gemini batch {label}: {len(group_jobs)} clips", flush=True)
            payload = batch_payload(args.voice, group_jobs, label)
            url = generate_tts.GENERATE_CONTENT_URL.format(model=MODEL)
            response = generate_tts.api_call(api_key, payload, args.retries, url)
            audio, mime, rate = generate_tts.extract_audio(response)
            generate_tts.write_master(master, audio, mime, rate)
        else:
            print(f"[{position}/4] reuse batch {label}", flush=True)
        split_batch(master, group_jobs, config["audio"]["appBitrate"], index["items"])
        generate_tts.dump_json(index_path, index)
        print(f"[{position}/4] indexed {len(index['items'])}/88", flush=True)

    if len(index["items"]) != 88:
        raise SystemExit(f"expected 88 clips, got {len(index['items'])}")
    print(f"Completed {args.voice}: 88/88 clips from four Gemini batches")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except Exception as error:
        print(f"ERROR: {error}", file=sys.stderr)
        raise
