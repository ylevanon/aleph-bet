#!/usr/bin/env python3
"""Validate the Hebrew resource pack without network access."""

from __future__ import annotations

import hashlib
import json
import re
import sys
import unicodedata
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
PACK = ROOT / "asset-pack"
HEBREW_BASE = re.compile(r"[\u05D0-\u05EA]")
NIQQUD = re.compile(r"[\u0591-\u05C7]")
ID = re.compile(r"^[a-z0-9_]+$")


def load(path: Path) -> dict[str, Any]:
    with path.open(encoding="utf-8") as handle:
        return json.load(handle)


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def assert_nfc(value: Any, path: str = "$ROOT") -> None:
    if isinstance(value, str):
        require(value == unicodedata.normalize("NFC", value), f"non-NFC string at {path}: {value!r}")
    elif isinstance(value, list):
        for index, item in enumerate(value):
            assert_nfc(item, f"{path}[{index}]")
    elif isinstance(value, dict):
        for key, item in value.items():
            assert_nfc(item, f"{path}.{key}")


def strip_points(text: str) -> str:
    return NIQQUD.sub("", text)


def validate_alphabet() -> int:
    data = load(PACK / "data/alphabet.json")
    letters = data["letters"]
    require(len(letters) == 27, "alphabet must contain 22 base letters plus 5 final forms")
    ids = [letter["id"] for letter in letters]
    glyphs = [letter["glyph"] for letter in letters]
    require(len(ids) == len(set(ids)), "alphabet ids must be unique")
    require(len(glyphs) == len(set(glyphs)), "alphabet glyphs must be unique")
    require(sum(bool(letter["finalForm"]) for letter in letters) == 5, "alphabet must have five final forms")
    for letter in letters:
        require(ID.fullmatch(letter["id"]) is not None, f"invalid alphabet id {letter['id']}")
        require(HEBREW_BASE.search(letter["glyph"]) is not None, f"invalid glyph for {letter['id']}")
        require(NIQQUD.search(letter["namePointed"]) is not None, f"unpointed letter name {letter['id']}")
    assert_nfc(data, "alphabet")
    return len(letters)


def validate_niqqud() -> int:
    data = load(PACK / "data/niqqud.json")
    marks = data["marks"]
    require(len(marks) == 16, "niqqud inventory must contain 16 practical marks")
    ids = [mark["id"] for mark in marks]
    require(len(ids) == len(set(ids)), "niqqud ids must be unique")
    for mark in marks:
        require(ID.fullmatch(mark["id"]) is not None, f"invalid niqqud id {mark['id']}")
        require(mark["mark"], f"missing mark for {mark['id']}")
        require(HEBREW_BASE.search(mark["samplePointed"]) is not None, f"missing sound sample for {mark['id']}")
    assert_nfc(data, "niqqud")
    return len(marks)


def validate_words() -> tuple[int, int]:
    data = load(PACK / "data/words.json")
    words = data["words"]
    require(len(words) == 100, "word inventory must contain exactly 100 ranked forms")
    require([word["rank"] for word in words] == list(range(1, 101)), "word ranks must be 1..100")
    expected_source_ranks = list(range(1, 34)) + list(range(35, 102))
    require([word["sourceRank"] for word in words] == expected_source_ranks, "source ranks must only exclude source token 34")
    counts = [word["sourceCount"] for word in words]
    require(counts == sorted(counts, reverse=True), "source counts must be descending")
    sense_ids: set[str] = set()
    senses = 0
    for word in words:
        require(HEBREW_BASE.search(word["unpointed"]) is not None, f"invalid word at rank {word['rank']}")
        require(word["senses"], f"rank {word['rank']} has no senses")
        normalized_forms = {strip_points(sense["pointed"]) for sense in word["senses"]}
        if "spellingNote" not in word["senses"][0]:
            require(word["unpointed"] in normalized_forms, f"pointed spelling mismatch at rank {word['rank']}")
        for sense in word["senses"]:
            sense_id = sense["id"]
            require(ID.fullmatch(sense_id) is not None, f"invalid word sense id {sense_id}")
            require(sense_id not in sense_ids, f"duplicate word sense id {sense_id}")
            require(NIQQUD.search(sense["pointed"]) is not None, f"unpointed sense {sense_id}")
            require(sense["latin"] and sense["gloss"], f"incomplete sense {sense_id}")
            sense_ids.add(sense_id)
            senses += 1
    assert_nfc(data, "words")
    snapshot_path = PACK / "data" / data["source"]["localSnapshot"]
    snapshot_lines = snapshot_path.read_text(encoding="utf-8").splitlines()
    require(snapshot_lines[0] == "rank\tsource_rank\tword\tcount", "invalid frequency snapshot header")
    require(len(snapshot_lines) == 101, "frequency snapshot must contain 100 rows")
    for word, line in zip(words, snapshot_lines[1:], strict=True):
        rank, source_rank, surface, count = line.split("\t")
        require(int(rank) == word["rank"], f"snapshot rank mismatch for {surface}")
        require(int(source_rank) == word["sourceRank"], f"snapshot source rank mismatch for {surface}")
        require(surface == word["unpointed"], f"snapshot word mismatch at rank {rank}")
        require(int(count) == word["sourceCount"], f"snapshot count mismatch for {surface}")
    return len(words), senses


def validate_jobs(expected_senses: int) -> int:
    plan = load(PACK / "audio/jobs.json")
    jobs = plan["jobs"]
    expected = 27 + 29 + (16 * 2) + (expected_senses * 2)
    require(plan["jobCount"] == len(jobs) == expected, f"expected {expected} audio jobs")
    ids = [job["id"] for job in jobs]
    require(len(ids) == len(set(ids)), "audio job ids must be unique")
    for job in jobs:
        request = job["request"]
        require(request["model"] == plan["model"], f"model drift in {job['id']}")
        require(request["response_format"] == {"type": "audio"}, f"invalid response format in {job['id']}")
        require(job["transcript"] in request["input"], f"transcript missing from request {job['id']}")
        require(job["output"].endswith(".mp3"), f"non-app audio format in {job['id']}")
    review = load(PACK / "audio/review.json")["items"]
    require(set(review) >= set(ids), "review checklist does not cover every planned job")
    catalog = load(PACK / "audio/catalog.json")
    require(catalog["model"] == plan["model"], "audio catalog model drift")
    require(catalog["voice"] == plan["voice"], "audio catalog voice drift")
    expected_catalog = [
        {
            "id": job["id"],
            "category": job["category"],
            "itemId": job["itemId"],
            "profile": job["profile"],
            "transcript": job["transcript"],
            "path": job["output"],
        }
        for job in jobs
    ]
    require(catalog["items"] == expected_catalog, "audio catalog does not match TTS plan")
    return len(jobs)


def validate_visuals() -> int:
    font = PACK / "fonts/noto_sans_hebrew.ttf"
    license_file = PACK / "fonts/OFL.txt"
    require(font.read_bytes()[:4] in {b"\x00\x01\x00\x00", b"OTTO"}, "invalid font file")
    require("SIL OPEN FONT LICENSE" in license_file.read_text(encoding="utf-8"), "missing OFL license")
    svgs = list((PACK / "visual/svg").glob("*.svg"))
    require(len(svgs) >= 4, "visual preview set is incomplete")
    for svg in svgs:
        ET.parse(svg)
    return len(svgs)


def validate_generated_audio_if_present() -> int:
    index_path = PACK / "audio/audio-index.json"
    if not index_path.exists():
        return 0
    items = load(index_path)["items"]
    for job_id, item in items.items():
        path = PACK / "audio" / item["path"]
        require(path.exists(), f"indexed audio missing for {job_id}")
        digest = hashlib.sha256(path.read_bytes()).hexdigest()
        require(digest == item["sha256"], f"audio hash mismatch for {job_id}")
    return len(items)


def main() -> int:
    try:
        letters = validate_alphabet()
        marks = validate_niqqud()
        words, senses = validate_words()
        jobs = validate_jobs(senses)
        svgs = validate_visuals()
        generated = validate_generated_audio_if_present()
    except (AssertionError, KeyError, json.JSONDecodeError, OSError, ET.ParseError) as error:
        print(f"VALIDATION FAILED: {error}", file=sys.stderr)
        return 1
    print(
        f"OK: {letters} letter forms, {marks} marks, {words} ranked words, "
        f"{senses} pronunciations, {jobs} TTS calls, {svgs} SVGs, {generated} generated clips"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
