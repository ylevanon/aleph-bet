#!/usr/bin/env python3
"""Merge isolated Sulafat/Iapetus foundation workers into shipping manifests."""

from __future__ import annotations

import hashlib
import json
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
AUDIO = ROOT / "asset-pack" / "audio"
VOICES = ("Sulafat", "Iapetus")
EXPECTED_PER_VOICE = 88


def load(path: Path) -> dict[str, Any]:
    with path.open(encoding="utf-8") as handle:
        return json.load(handle)


def dump(path: Path, value: Any) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    temporary = path.with_suffix(path.suffix + ".tmp")
    with temporary.open("w", encoding="utf-8", newline="\n") as handle:
        json.dump(value, handle, ensure_ascii=False, indent=2)
        handle.write("\n")
    temporary.replace(path)


def asset_id(voice: str, semantic_id: str) -> str:
    return f"{voice.lower()}.{semantic_id}"


def main() -> int:
    combined_jobs: list[dict[str, Any]] = []
    combined_catalog: list[dict[str, Any]] = []
    combined_index: dict[str, Any] = {}

    for voice in VOICES:
        tag = voice.lower()
        worker = AUDIO / "workers"
        plan = load(worker / f"{tag}-jobs.json")
        catalog = load(worker / f"{tag}-catalog.json")
        index = load(worker / f"{tag}-index.json")["items"]
        jobs = plan["jobs"]
        if plan.get("voice") != voice:
            raise SystemExit(f"worker voice mismatch for {tag}")
        if len(jobs) != EXPECTED_PER_VOICE or len(catalog["items"]) != EXPECTED_PER_VOICE:
            raise SystemExit(f"{voice}: expected {EXPECTED_PER_VOICE} planned jobs")
        if len(index) != EXPECTED_PER_VOICE:
            raise SystemExit(f"{voice}: generated {len(index)}/{EXPECTED_PER_VOICE} clips")

        for job, catalog_item in zip(jobs, catalog["items"], strict=True):
            semantic_id = job["id"]
            unique_id = asset_id(voice, semantic_id)
            if semantic_id not in index:
                raise SystemExit(f"{voice}: index is missing {semantic_id}")
            indexed = index[semantic_id]
            output = AUDIO / indexed["path"]
            if not output.is_file():
                raise SystemExit(f"{voice}: output is missing: {output}")
            actual_hash = hashlib.sha256(output.read_bytes()).hexdigest()
            if indexed.get("sha256") != actual_hash:
                raise SystemExit(f"{voice}: hash mismatch for {semantic_id}")

            combined_jobs.append({**job, "id": unique_id, "semanticId": semantic_id})
            combined_catalog.append({
                **catalog_item,
                "id": unique_id,
                "semanticId": semantic_id,
                "voice": voice,
            })
            combined_index[unique_id] = {**indexed, "semanticId": semantic_id}

    model = combined_jobs[0]["model"]
    dump(AUDIO / "foundation-jobs.json", {
        "schemaVersion": 1,
        "model": model,
        "voices": list(VOICES),
        "jobCount": len(combined_jobs),
        "jobs": combined_jobs,
    })
    dump(AUDIO / "foundation-catalog.json", {
        "schemaVersion": 1,
        "model": model,
        "language": "he-IL",
        "primaryVoice": "Sulafat",
        "secondaryVoice": "Iapetus",
        "voices": {"female": "Sulafat", "male": "Iapetus"},
        "items": combined_catalog,
    })
    dump(AUDIO / "foundation-audio-index.json", {
        "schemaVersion": 1,
        "items": combined_index,
    })

    canonical_path = AUDIO / "audio-index.json"
    canonical = load(canonical_path) if canonical_path.exists() else {"schemaVersion": 1, "items": {}}
    canonical_items = canonical.setdefault("items", {})
    for key in list(canonical_items):
        if key.startswith(("sulafat.alphabet.", "sulafat.niqqud.", "iapetus.alphabet.", "iapetus.niqqud.")):
            del canonical_items[key]
    canonical_items.update(combined_index)
    dump(canonical_path, canonical)

    review_path = AUDIO / "foundation-review.json"
    review = load(review_path) if review_path.exists() else {"schemaVersion": 1, "items": {}}
    review_items = review.setdefault("items", {})
    active_ids = set(combined_index)
    for unique_id in active_ids:
        review_items.setdefault(unique_id, {
            "status": "pending",
            "reviewer": None,
            "reviewedAt": None,
            "notes": "",
        })
    for unique_id in list(review_items):
        if unique_id not in active_ids and review_items[unique_id].get("status") == "pending":
            del review_items[unique_id]
    dump(review_path, review)

    print(f"Merged {len(combined_index)} foundation clips across {len(VOICES)} voices")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
