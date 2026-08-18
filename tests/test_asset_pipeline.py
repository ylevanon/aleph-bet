from __future__ import annotations

import base64
import io
import json
import math
import shutil
import struct
import subprocess
import sys
import tempfile
import unittest
import wave
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "scripts"))

import generate_tts  # noqa: E402


class GeminiContractTests(unittest.TestCase):
    def setUp(self) -> None:
        self.audio = b"\x01\x00\x02\x00"
        self.encoded = base64.b64encode(self.audio).decode("ascii")

    def test_extracts_sdk_snake_case_audio(self) -> None:
        actual, mime, rate = generate_tts.extract_audio({
            "output_audio": {"data": self.encoded, "mime_type": "audio/l16", "sample_rate": 24000}
        })
        self.assertEqual(actual, self.audio)
        self.assertEqual(mime, "audio/l16")
        self.assertEqual(rate, 24000)

    def test_extracts_rest_camel_case_audio(self) -> None:
        actual, mime, rate = generate_tts.extract_audio({
            "outputAudio": {"data": self.encoded, "mimeType": "audio/l16", "sampleRate": 22050}
        })
        self.assertEqual(actual, self.audio)
        self.assertEqual(mime, "audio/l16")
        self.assertEqual(rate, 22050)

    def test_extracts_interactions_output_block(self) -> None:
        actual, _, _ = generate_tts.extract_audio({
            "outputs": [{"type": "audio", "data": self.encoded, "mime_type": "audio/l16"}]
        })
        self.assertEqual(actual, self.audio)

    def test_extracts_nested_interactions_content(self) -> None:
        actual, _, _ = generate_tts.extract_audio({
            "outputs": [{"type": "model_output", "content": [{"type": "audio", "data": self.encoded}]}]
        })
        self.assertEqual(actual, self.audio)

    def test_extracts_deep_preview_audio_envelope(self) -> None:
        actual, _, _ = generate_tts.extract_audio({
            "interaction": {
                "outputs": [{"content": {"parts": [{"type": "audio", "data": self.encoded}]}}]
            }
        })
        self.assertEqual(actual, self.audio)

    def test_extracts_legacy_generate_content_shape(self) -> None:
        actual, _, _ = generate_tts.extract_audio({
            "candidates": [{"content": {"parts": [{"inlineData": {"data": self.encoded, "mimeType": "audio/L16"}}]}}]
        })
        self.assertEqual(actual, self.audio)

    def test_rejects_response_without_audio(self) -> None:
        with self.assertRaisesRegex(RuntimeError, "did not contain inline audio"):
            generate_tts.extract_audio({"outputs": []})

    def test_builds_generate_content_request_for_25_tts(self) -> None:
        config = generate_tts.load_json(generate_tts.CONFIG_PATH)
        config["model"] = "gemini-2.5-flash-preview-tts"
        job = generate_tts.build_jobs(config, "Sulafat")[0]
        request = generate_tts.make_request(config, job)
        self.assertEqual(request["generationConfig"]["responseModalities"], ["AUDIO"])
        voice = request["generationConfig"]["speechConfig"]["voiceConfig"]["prebuiltVoiceConfig"]
        self.assertEqual(voice["voiceName"], "Sulafat")
        self.assertIn(job.transcript, request["contents"][0]["parts"][0]["text"])


class AudioPipelineTests(unittest.TestCase):
    def make_pcm(self, seconds: float = 0.15, sample_rate: int = 24000) -> bytes:
        frames = int(seconds * sample_rate)
        return b"".join(
            struct.pack("<h", int(6000 * math.sin(2 * math.pi * 440 * index / sample_rate)))
            for index in range(frames)
        )

    def test_wraps_raw_pcm_as_valid_wave(self) -> None:
        pcm = self.make_pcm()
        with tempfile.TemporaryDirectory() as directory:
            target = Path(directory) / "sample.wav"
            generate_tts.write_master(target, pcm, "audio/l16", 24000)
            with wave.open(str(target), "rb") as reader:
                self.assertEqual(reader.getnchannels(), 1)
                self.assertEqual(reader.getsampwidth(), 2)
                self.assertEqual(reader.getframerate(), 24000)
                self.assertEqual(reader.readframes(reader.getnframes()), pcm)

    def test_does_not_double_wrap_riff_data(self) -> None:
        memory = io.BytesIO()
        with wave.open(memory, "wb") as writer:
            writer.setnchannels(1)
            writer.setsampwidth(2)
            writer.setframerate(24000)
            writer.writeframes(self.make_pcm())
        source = memory.getvalue()
        with tempfile.TemporaryDirectory() as directory:
            target = Path(directory) / "sample.wav"
            generate_tts.write_master(target, source, "audio/l16", 24000)
            self.assertEqual(target.read_bytes(), source)

    @unittest.skipUnless(shutil.which("ffmpeg") and shutil.which("ffprobe"), "ffmpeg tools unavailable")
    def test_encodes_and_probes_app_mp3(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            directory_path = Path(directory)
            master = directory_path / "master.wav"
            output = directory_path / "app.mp3"
            generate_tts.write_master(master, self.make_pcm(), "audio/l16", 24000)
            generate_tts.encode_mp3(master, output, "64k")
            details = generate_tts.probe(output)
            self.assertEqual(details["codec"], "mp3")
            self.assertEqual(details["sampleRateHz"], 24000)
            self.assertEqual(details["channels"], 1)
            self.assertGreater(details["durationSeconds"], 0)
            self.assertEqual(len(details["sha256"]), 64)


class ResourcePlanTests(unittest.TestCase):
    def test_full_plan_has_expected_shape(self) -> None:
        config = generate_tts.load_json(generate_tts.CONFIG_PATH)
        jobs = generate_tts.build_jobs(config, config["defaultVoice"])
        self.assertEqual(len(jobs), 312)
        self.assertEqual(len({job.id for job in jobs}), 312)
        self.assertEqual(sum(job.category == "words" for job in jobs), 224)
        self.assertEqual(sum(job.category == "alphabet" for job in jobs), 56)
        self.assertEqual(sum(job.category == "niqqud" for job in jobs), 32)

    def test_materialized_requests_match_fresh_plan(self) -> None:
        config = generate_tts.load_json(generate_tts.CONFIG_PATH)
        planned = generate_tts.load_json(generate_tts.JOBS_PATH)
        fresh = generate_tts.build_jobs(config, config["defaultVoice"])
        expected = [generate_tts.job_record(config, job) for job in fresh]
        self.assertEqual(planned["jobs"], expected)

    def test_every_frequency_word_has_two_audio_profiles_per_sense(self) -> None:
        words = json.loads((ROOT / "asset-pack/data/words.json").read_text(encoding="utf-8"))["words"]
        plan = generate_tts.load_json(generate_tts.JOBS_PATH)["jobs"]
        word_jobs = {record["id"] for record in plan if record["category"] == "words"}
        for word in words:
            for sense in word["senses"]:
                for profile in ("natural", "teaching"):
                    self.assertIn(f"word.{sense['id']}.{profile}", word_jobs)


class FontCoverageTests(unittest.TestCase):
    @unittest.skipUnless(shutil.which("hb-shape"), "HarfBuzz tooling unavailable")
    def test_bundled_font_shapes_every_hebrew_resource_string(self) -> None:
        strings: list[str] = []
        for filename in ("alphabet.json", "niqqud.json", "words.json"):
            value = json.loads((ROOT / "asset-pack/data" / filename).read_text(encoding="utf-8"))

            def collect(item: object) -> None:
                if isinstance(item, str) and any("\u0590" <= char <= "\u05ff" for char in item):
                    strings.append(item)
                elif isinstance(item, list):
                    for child in item:
                        collect(child)
                elif isinstance(item, dict):
                    for child in item.values():
                        collect(child)

            collect(value)
        command = [
            "hb-shape",
            str(ROOT / "asset-pack/fonts/noto_sans_hebrew.ttf"),
            " · ".join(strings),
            "--output-format=json",
        ]
        result = subprocess.run(command, check=True, capture_output=True, text=True)
        self.assertNotIn(".notdef", result.stdout)


class VisualAssetTests(unittest.TestCase):
    @unittest.skipUnless(shutil.which("rsvg-convert"), "SVG renderer unavailable")
    def test_every_svg_reference_renders(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            for svg in (ROOT / "asset-pack/visual/svg").glob("*.svg"):
                output = Path(directory) / f"{svg.stem}.png"
                subprocess.run(
                    ["rsvg-convert", "-o", str(output), str(svg)],
                    check=True,
                    capture_output=True,
                )
                self.assertTrue(output.read_bytes().startswith(b"\x89PNG"), svg.name)


if __name__ == "__main__":
    unittest.main()
