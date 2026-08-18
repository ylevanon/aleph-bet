#!/usr/bin/env python3
"""Plan and generate the Aleph Bet Hebrew audio pack with Gemini TTS.

No third-party Python package is required. Planning is offline and is the default.
Execution requires GEMINI_API_KEY and ffmpeg.
"""

from __future__ import annotations

import argparse
import base64
import hashlib
import json
import os
import random
import re
import shutil
import subprocess
import sys
import time
import urllib.error
import urllib.request
import wave
from dataclasses import dataclass
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Iterable


ROOT = Path(__file__).resolve().parents[1]
PACK = ROOT / "asset-pack"
DATA = PACK / "data"
AUDIO = PACK / "audio"
CONFIG_PATH = AUDIO / "tts-config.json"
JOBS_PATH = AUDIO / "jobs.json"
INDEX_PATH = AUDIO / "audio-index.json"
CATALOG_PATH = AUDIO / "catalog.json"
REVIEW_PATH = AUDIO / "review.json"
INTERACTIONS_URL = "https://generativelanguage.googleapis.com/v1beta/interactions"
GENERATE_CONTENT_URL = "https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent"


@dataclass(frozen=True)
class Job:
    id: str
    category: str
    item_id: str
    profile: str
    transcript: str
    voice: str
    relative_output: str


def load_json(path: Path) -> dict[str, Any]:
    with path.open(encoding="utf-8") as handle:
        return json.load(handle)


def dump_json(path: Path, value: Any) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    temporary = path.with_suffix(path.suffix + ".tmp")
    with temporary.open("w", encoding="utf-8", newline="\n") as handle:
        json.dump(value, handle, ensure_ascii=False, indent=2)
        handle.write("\n")
    temporary.replace(path)


def make_request(config: dict[str, Any], job: Job) -> dict[str, Any]:
    instruction = config["profiles"][job.profile]
    prompt = f"{instruction}\n\nTRANSCRIPT:\n{job.transcript}"
    if config["model"].startswith("gemini-2.5-"):
        return {
            "contents": [{"parts": [{"text": prompt}]}],
            "generationConfig": {
                "responseModalities": ["AUDIO"],
                "speechConfig": {
                    "voiceConfig": {
                        "prebuiltVoiceConfig": {"voiceName": job.voice},
                    }
                },
            },
        }
    return {
        "model": config["model"],
        "input": prompt,
        "response_format": {"type": "audio"},
        "generation_config": {"speech_config": [{"voice": job.voice}]},
    }


def build_jobs(config: dict[str, Any], voice: str) -> list[Job]:
    jobs: list[Job] = []
    voice_slug = voice.lower()
    alphabet = load_json(DATA / "alphabet.json")["letters"]
    niqqud = load_json(DATA / "niqqud.json")["marks"]
    words = load_json(DATA / "words.json")["words"]

    for letter in alphabet:
        item_id = letter["id"]
        jobs.append(Job(
            id=f"alphabet.{item_id}.name",
            category="alphabet",
            item_id=item_id,
            profile="letter_name",
            transcript=letter["namePointed"],
            voice=voice,
            relative_output=f"generated/{voice_slug}/alphabet/{item_id}/name.mp3",
        ))
        for sample in letter.get("soundSamples", []):
            sample_id = sample["id"]
            jobs.append(Job(
                id=f"alphabet.{item_id}.sound.{sample_id}",
                category="alphabet",
                item_id=item_id,
                profile="sound",
                transcript=sample["pointed"],
                voice=voice,
                relative_output=f"generated/{voice_slug}/alphabet/{item_id}/sound-{sample_id}.mp3",
            ))

    for mark in niqqud:
        item_id = mark["id"]
        jobs.append(Job(
            id=f"niqqud.{item_id}.name",
            category="niqqud",
            item_id=item_id,
            profile="mark_name",
            transcript=mark["namePointed"],
            voice=voice,
            relative_output=f"generated/{voice_slug}/niqqud/{item_id}/name.mp3",
        ))
        jobs.append(Job(
            id=f"niqqud.{item_id}.sound",
            category="niqqud",
            item_id=item_id,
            profile="sound",
            transcript=mark["samplePointed"],
            voice=voice,
            relative_output=f"generated/{voice_slug}/niqqud/{item_id}/sound.mp3",
        ))

    for word in words:
        for sense in word["senses"]:
            item_id = sense["id"]
            for profile in ("natural", "teaching"):
                jobs.append(Job(
                    id=f"word.{item_id}.{profile}",
                    category="words",
                    item_id=item_id,
                    profile=profile,
                    transcript=sense["pointed"],
                    voice=voice,
                    relative_output=f"generated/{voice_slug}/words/{item_id}/{profile}.mp3",
                ))
    return jobs


def audition_jobs(config: dict[str, Any]) -> list[Job]:
    return [
        Job(
            id=f"audition.{voice}",
            category="audition",
            item_id=voice,
            profile="natural",
            transcript=config["auditionTranscript"],
            voice=voice,
            relative_output=f"auditions/{voice.lower()}.mp3",
        )
        for voice in config["auditionVoices"]
    ]


def job_record(config: dict[str, Any], job: Job) -> dict[str, Any]:
    return {
        "id": job.id,
        "category": job.category,
        "itemId": job.item_id,
        "profile": job.profile,
        "transcript": job.transcript,
        "voice": job.voice,
        "model": config["model"],
        "output": job.relative_output,
        "request": make_request(config, job),
    }


def extract_audio(response: dict[str, Any]) -> tuple[bytes, str, int]:
    # The REST schema uses outputs; SDKs also expose output_audio convenience data.
    candidates: list[dict[str, Any]] = []
    for key in ("output_audio", "outputAudio"):
        value = response.get(key)
        if isinstance(value, dict):
            candidates.append(value)
    for output in response.get("outputs", []):
        if isinstance(output, dict):
            candidates.append(output)
            for content in output.get("content", []):
                if isinstance(content, dict):
                    candidates.append(content)

    for candidate in candidates:
        if candidate.get("data"):
            mime = candidate.get("mime_type") or candidate.get("mimeType") or "audio/l16"
            rate = int(candidate.get("sample_rate") or candidate.get("sampleRate") or 24000)
            return base64.b64decode(candidate["data"]), mime, rate

    # Interactions response envelopes have evolved during Preview. Locate an
    # inline audio block recursively while remaining compatible with old shapes.
    def descendants(value: Any) -> Iterable[dict[str, Any]]:
        if isinstance(value, dict):
            yield value
            for child in value.values():
                yield from descendants(child)
        elif isinstance(value, list):
            for child in value:
                yield from descendants(child)

    for candidate in descendants(response):
        encoded = candidate.get("data")
        if not isinstance(encoded, str) or not encoded:
            continue
        mime = candidate.get("mime_type") or candidate.get("mimeType") or "audio/l16"
        kind = str(candidate.get("type", "")).lower()
        if kind == "audio" or str(mime).lower().startswith("audio/"):
            rate = int(candidate.get("sample_rate") or candidate.get("sampleRate") or 24000)
            return base64.b64decode(encoded), str(mime), rate

    # Defensive support for generateContent-shaped responses if the endpoint evolves.
    try:
        inline = response["candidates"][0]["content"]["parts"][0]
        inline = inline.get("inlineData") or inline.get("inline_data")
        mime = inline.get("mimeType") or inline.get("mime_type") or "audio/l16"
        return base64.b64decode(inline["data"]), mime, 24000
    except (KeyError, IndexError, TypeError, AttributeError) as error:
        keys = ", ".join(sorted(response))
        status = response.get("status", "unknown")
        raise RuntimeError(f"Gemini response did not contain inline audio data (status={status}, keys={keys})") from error


def api_call(
    api_key: str,
    payload: dict[str, Any],
    retries: int,
    api_url: str = INTERACTIONS_URL,
) -> dict[str, Any]:
    body = json.dumps(payload, ensure_ascii=False).encode("utf-8")
    request = urllib.request.Request(
        api_url,
        data=body,
        method="POST",
        headers={
            "Content-Type": "application/json",
            "x-goog-api-key": api_key,
            **({"Api-Revision": "2026-05-20"} if api_url == INTERACTIONS_URL else {}),
        },
    )
    for attempt in range(retries + 1):
        delay: float | None = None
        try:
            with urllib.request.urlopen(request, timeout=120) as response:
                return json.loads(response.read())
        except urllib.error.HTTPError as error:
            detail = error.read().decode("utf-8", errors="replace")
            retryable = error.code == 429 or 500 <= error.code < 600
            if not retryable or attempt == retries:
                raise RuntimeError(f"Gemini HTTP {error.code}: {detail[:1000]}") from error
            server_wait = re.search(r"Please retry in\s+([0-9.]+)s", detail)
            if error.code == 429 and server_wait:
                delay = min(60.0, float(server_wait.group(1)) + 1.0 + random.random())
        except (urllib.error.URLError, TimeoutError) as error:
            if attempt == retries:
                raise RuntimeError(f"Gemini request failed: {error}") from error
        if delay is None:
            delay = min(30.0, (2**attempt) + random.random())
        time.sleep(delay)
    raise AssertionError("unreachable")


def write_master(path: Path, audio: bytes, mime: str, sample_rate: int) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    if audio.startswith(b"RIFF"):
        path.write_bytes(audio)
        return
    with wave.open(str(path), "wb") as output:
        output.setnchannels(1)
        output.setsampwidth(2)
        output.setframerate(sample_rate)
        output.writeframes(audio)


def encode_mp3(master: Path, output: Path, bitrate: str) -> None:
    output.parent.mkdir(parents=True, exist_ok=True)
    temporary = output.with_suffix(".tmp.mp3")
    command = [
        "ffmpeg", "-hide_banner", "-loglevel", "error", "-y",
        "-i", str(master), "-map_metadata", "-1", "-ac", "1",
        "-ar", "24000", "-codec:a", "libmp3lame", "-b:a", bitrate,
        str(temporary),
    ]
    subprocess.run(command, check=True)
    temporary.replace(output)


def probe(path: Path) -> dict[str, Any]:
    command = [
        "ffprobe", "-v", "error", "-select_streams", "a:0",
        "-show_entries", "stream=codec_name,sample_rate,channels,duration",
        "-of", "json", str(path),
    ]
    result = subprocess.run(command, check=True, capture_output=True, text=True)
    stream = json.loads(result.stdout)["streams"][0]
    return {
        "codec": stream.get("codec_name"),
        "sampleRateHz": int(stream.get("sample_rate", 0)),
        "channels": int(stream.get("channels", 0)),
        "durationSeconds": round(float(stream.get("duration", 0)), 3),
        "bytes": path.stat().st_size,
        "sha256": hashlib.sha256(path.read_bytes()).hexdigest(),
    }


def initialize_review(jobs: Iterable[Job], review_path: Path, *, prune_global: bool = True) -> None:
    existing = load_json(review_path) if review_path.exists() else {"schemaVersion": 1, "items": {}}
    items = existing.setdefault("items", {})
    for job in jobs:
        items.setdefault(job.id, {
            "status": "pending",
            "reviewer": None,
            "reviewedAt": None,
            "notes": "",
        })
    if prune_global:
        active_ids: set[str] = set()
        for plan_path in (JOBS_PATH, AUDIO / "audition-jobs.json"):
            if plan_path.exists():
                active_ids.update(record["id"] for record in load_json(plan_path)["jobs"])
        for job_id in list(items):
            if job_id not in active_ids and items[job_id].get("status") == "pending":
                del items[job_id]
    dump_json(review_path, existing)


def select_jobs(jobs: list[Job], scope: str, contains: str | None, limit: int | None) -> list[Job]:
    selected = (
        jobs
        if scope == "all"
        else [job for job in jobs if job.category in {"alphabet", "niqqud"}]
        if scope == "foundation"
        else [job for job in jobs if job.category == scope]
    )
    if contains:
        selected = [job for job in selected if contains in job.id]
    return selected[:limit] if limit else selected


def generate(
    config: dict[str, Any],
    jobs: list[Job],
    args: argparse.Namespace,
    index_path: Path = INDEX_PATH,
) -> int:
    if shutil.which("ffmpeg") is None or shutil.which("ffprobe") is None:
        raise SystemExit("ffmpeg and ffprobe are required for --execute")
    api_key = os.environ.get(args.api_key_env)
    if not api_key and args.env_file:
        env_path = args.env_file.expanduser().resolve()
        if not env_path.is_file():
            raise SystemExit(f"env file not found: {env_path}")
        for raw_line in env_path.read_text(encoding="utf-8").splitlines():
            line = raw_line.strip()
            if not line or line.startswith("#") or "=" not in line:
                continue
            name, value = line.split("=", 1)
            if name.strip() == args.api_key_env:
                api_key = value.strip().strip('"').strip("'")
                break
    if not api_key:
        location = f" or present in {args.env_file}" if args.env_file else ""
        raise SystemExit(f"{args.api_key_env} is not set{location}. Planning requires no key; --execute does.")

    index = load_json(index_path) if index_path.exists() else {"schemaVersion": 1, "items": {}}
    items = index.setdefault("items", {})
    failures = 0
    calls_started = 0
    for position, job in enumerate(jobs, 1):
        output = AUDIO / job.relative_output
        if output.exists() and not args.force:
            print(f"[{position}/{len(jobs)}] skip {job.id}")
            continue
        print(f"[{position}/{len(jobs)}] generate {job.id}")
        master_dir = AUDIO / "masters" / job.voice / job.category / job.item_id
        master = master_dir / f"{job.profile}.wav"
        try:
            if calls_started and args.delay_seconds:
                time.sleep(args.delay_seconds)
            calls_started += 1
            api_url = (
                GENERATE_CONTENT_URL.format(model=config["model"])
                if config["model"].startswith("gemini-2.5-")
                else INTERACTIONS_URL
            )
            response = api_call(api_key, make_request(config, job), args.retries, api_url)
            audio, mime, sample_rate = extract_audio(response)
            write_master(master, audio, mime, sample_rate)
            encode_mp3(master, output, config["audio"]["appBitrate"])
            details = probe(output)
            if details["durationSeconds"] <= 0 or details["channels"] != 1:
                raise RuntimeError(f"invalid audio probe: {details}")
            items[job.id] = {
                "path": job.relative_output,
                "transcript": job.transcript,
                "profile": job.profile,
                "voice": job.voice,
                "model": config["model"],
                "generatedAt": datetime.now(timezone.utc).isoformat(),
                **details,
            }
            dump_json(index_path, index)
            if not args.keep_masters:
                master.unlink(missing_ok=True)
        except Exception as error:  # keep the batch resumable
            failures += 1
            print(f"ERROR {job.id}: {error}", file=sys.stderr)
            if args.fail_fast:
                break
    return failures


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--execute", action="store_true", help="call Gemini; otherwise only write the offline job plan")
    parser.add_argument("--auditions", action="store_true", help="plan/generate the four voice auditions instead of the full pack")
    parser.add_argument("--voice", help="override tts-config.json defaultVoice for the full pack")
    parser.add_argument("--model", help="override the configured Gemini TTS model")
    parser.add_argument(
        "--scope",
        choices=("all", "foundation", "alphabet", "niqqud", "words", "audition"),
        default="all",
        help="foundation selects alphabet and niqqud while excluding words",
    )
    parser.add_argument("--contains", help="only jobs whose id contains this text")
    parser.add_argument("--limit", type=int, help="only the first N selected jobs")
    parser.add_argument("--api-key-env", default="GEMINI_API_KEY")
    parser.add_argument("--env-file", type=Path, help="read only --api-key-env from an existing .env file; never copied or logged")
    parser.add_argument("--retries", type=int, default=4)
    parser.add_argument(
        "--delay-seconds",
        type=float,
        default=0.0,
        help="minimum pause between new Gemini calls in this worker (useful for shared free-tier quotas)",
    )
    parser.add_argument("--force", action="store_true")
    parser.add_argument("--keep-masters", action="store_true")
    parser.add_argument("--fail-fast", action="store_true")
    parser.add_argument(
        "--worker-tag",
        help="isolate plan, catalog, review, and index files under audio/workers for safe parallel execution",
    )
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    config = load_json(CONFIG_PATH)
    if args.model:
        config = {**config, "model": args.model}
    voice = args.voice or config["defaultVoice"]
    jobs = audition_jobs(config) if args.auditions else build_jobs(config, voice)
    jobs = select_jobs(jobs, args.scope, args.contains, args.limit)
    plan = {
        "schemaVersion": 1,
        "generatedAt": datetime.now(timezone.utc).isoformat(),
        "model": config["model"],
        "voice": None if args.auditions else voice,
        "jobCount": len(jobs),
        "jobs": [job_record(config, job) for job in jobs],
    }
    if args.worker_tag:
        safe_tag = "".join(char for char in args.worker_tag.lower() if char.isalnum() or char in "-_")
        if not safe_tag or safe_tag != args.worker_tag.lower():
            raise SystemExit("--worker-tag may only contain letters, numbers, hyphens, and underscores")
        worker_dir = AUDIO / "workers"
        plan_path = worker_dir / f"{safe_tag}-jobs.json"
        catalog_path = worker_dir / f"{safe_tag}-catalog.json"
        review_path = worker_dir / f"{safe_tag}-review.json"
        index_path = worker_dir / f"{safe_tag}-index.json"
    else:
        plan_path = AUDIO / ("audition-jobs.json" if args.auditions else "jobs.json")
        catalog_path = CATALOG_PATH
        review_path = REVIEW_PATH
        index_path = INDEX_PATH
    dump_json(plan_path, plan)
    if not args.auditions:
        dump_json(catalog_path, {
            "schemaVersion": 1,
            "model": config["model"],
            "voice": voice,
            "items": [
                {
                    "id": job.id,
                    "category": job.category,
                    "itemId": job.item_id,
                    "profile": job.profile,
                    "transcript": job.transcript,
                    "path": job.relative_output,
                }
                for job in jobs
            ],
        })
    initialize_review(jobs, review_path, prune_global=not args.worker_tag)
    print(f"Wrote {len(jobs)} reproducible calls to {plan_path.relative_to(ROOT)}")
    if not args.execute:
        print("Plan only. Add --execute after setting GEMINI_API_KEY.")
        return 0
    failures = generate(config, jobs, args, index_path)
    print(f"Completed with {failures} failure(s). Index: {index_path.relative_to(ROOT)}")
    return 1 if failures else 0


if __name__ == "__main__":
    raise SystemExit(main())
