#!/usr/bin/env python3
"""Stage the asset pack into a Compose Multiplatform composeResources folder."""

from __future__ import annotations

import argparse
import hashlib
import json
import shutil
import sys
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
PACK = ROOT / "asset-pack"


def load(path: Path) -> dict[str, Any]:
    with path.open(encoding="utf-8") as handle:
        return json.load(handle)


def digest(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def copy(source: Path, destination: Path, root: Path, staged: list[dict[str, str]]) -> None:
    destination.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(source, destination)
    staged.append({"path": destination.relative_to(root).as_posix(), "sha256": digest(destination)})


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("destination", type=Path, help="the module's commonMain/composeResources directory")
    parser.add_argument("--require-audio", action="store_true", help="fail unless every catalogued MP3 exists")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    destination = args.destination.resolve()
    if destination in {Path("/"), Path.home().resolve(), ROOT.resolve()}:
        raise SystemExit(f"refusing unsafe destination: {destination}")

    staged: list[dict[str, str]] = []
    data_destination = destination / "files/hebrew"
    for filename in ("alphabet.json", "niqqud.json", "words.json", "frequency-2018-top100.tsv"):
        copy(PACK / "data" / filename, data_destination / filename, destination, staged)

    copy(PACK / "fonts/noto_sans_hebrew.ttf", destination / "font/noto_sans_hebrew.ttf", destination, staged)
    copy(PACK / "fonts/OFL.txt", destination / "files/licenses/noto_sans_hebrew_ofl.txt", destination, staged)

    source_catalog = load(PACK / "audio/catalog.json")
    app_catalog = {
        "schemaVersion": source_catalog["schemaVersion"],
        "model": source_catalog["model"],
        "voice": source_catalog["voice"],
        "items": [],
    }
    missing: list[str] = []
    for item in source_catalog["items"]:
        source = PACK / "audio" / item["path"]
        relative = Path(item["path"])
        if relative.parts[0] != "generated":
            raise SystemExit(f"unexpected catalog path: {relative}")
        app_relative = Path("files/audio/he").joinpath(*relative.parts[1:])
        app_item = dict(item)
        app_item["path"] = app_relative.as_posix()
        app_catalog["items"].append(app_item)
        if source.exists():
            copy(source, destination / app_relative, destination, staged)
        else:
            missing.append(item["id"])

    catalog_path = data_destination / "audio_catalog.json"
    catalog_path.parent.mkdir(parents=True, exist_ok=True)
    catalog_path.write_text(json.dumps(app_catalog, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    staged.append({"path": catalog_path.relative_to(destination).as_posix(), "sha256": digest(catalog_path)})

    manifest = {
        "schemaVersion": 1,
        "source": "Aleph Bet asset-pack",
        "stagedFileCount": len(staged),
        "expectedAudioCount": len(source_catalog["items"]),
        "stagedAudioCount": len(source_catalog["items"]) - len(missing),
        "missingAudioJobIds": missing,
        "files": staged,
    }
    manifest_path = data_destination / "resource_pack_manifest.json"
    manifest_path.write_text(json.dumps(manifest, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

    print(f"Staged {len(staged)} files into {destination}")
    print(f"Audio: {manifest['stagedAudioCount']}/{manifest['expectedAudioCount']}")
    print(f"Manifest: {manifest_path}")
    if args.require_audio and missing:
        print(f"ERROR: {len(missing)} audio files are still missing", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
