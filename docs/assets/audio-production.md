# Hebrew audio production guide

Status: generator and request pack ready; generation blocked only by credentials  
Last verified: 2026-08-13

## Recommendation

Use `gemini-3.1-flash-tts-preview` through the Gemini Developer API for the first production pass. It officially supports Hebrew, provides controllable delivery and 30 voices, and its current REST path is simple enough that this repository needs no Google SDK dependency. The chosen provisional voice is **Sulafat** because Google describes it as warm; Aoede, Kore, and Iapetus are included in the audition set.

Do not commit to the voice from its English demo. Generate the four Hebrew auditions, have a native Modern Israeli Hebrew speaker compare them, then change `defaultVoice` in `asset-pack/audio/tts-config.json` before generating the pack.

Gemini TTS is still Preview. Pinning the model, voice, prompt, transcript, request JSON, generation timestamp, output hash, and editorial result makes the output reproducible and replaceable if the preview changes. Google documents the current [TTS API, Hebrew support, models, and voices](https://ai.google.dev/gemini-api/docs/speech-generation) and the [current token pricing](https://ai.google.dev/gemini-api/docs/pricing).

## Why not use SVGs or TTS at runtime?

- Audio is generated once and bundled. The shipped V1 stays offline, fast, private, and consistent.
- Letters and niqqud are Unicode text rendered with Noto Sans Hebrew. SVG glyph exports would be harder to localize, theme, size, and make accessible, and can place niqqud incorrectly when a renderer substitutes fonts.
- MP3 at 24 kHz mono/64 kbps is the app copy. The Gemini response is preserved as a WAV master only when `--keep-masters` is passed.

## Inventory

The full plan has 312 calls:

| Scope | Calls | Notes |
|---|---:|---|
| Words | 224 | 112 pointed pronunciations × natural and teaching speeds |
| Alphabet | 56 | 27 names plus 29 sound/syllable examples |
| Niqqud and dots | 32 | 16 names plus 16 sound examples |

The 100 frequency ranks yield 112 pronunciations because unpointed Hebrew merges real homographs: `אֶת/אַתְּ`, `עִם/עַם`, `אִם/אֵם`, `אֶל/אַל`, `שָׁם/שֵׁם`, gendered forms, and others. Keeping these separate is essential for a beginner app.

## Generate safely

1. Create a Gemini API key in Google AI Studio. Never paste it into JSON, source, shell history, or a committed `.env` file.
2. Generate only auditions:

   ```bash
   GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --auditions --execute
   ```

   Or reuse an existing local `.env` without copying its secret into this repository:

   ```bash
   python3 scripts/generate_tts.py --auditions --execute \
     --env-file /absolute/path/to/existing-project/.env
   ```

3. Listen to `asset-pack/audio/auditions/*.mp3`. Ask a native speaker to evaluate accent, ח/כ, ר, stress, niqqud, and whether the model added or omitted anything.
4. Set `defaultVoice` in `asset-pack/audio/tts-config.json` and rebuild the full offline plan:

   ```bash
   python3 scripts/generate_tts.py
   ```

5. Smoke-test difficult entries before spending the batch:

   ```bash
   GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --execute --contains word.et_ --force
   GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --execute --contains word.im_ --force
   GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --execute --scope niqqud --limit 4
   ```

6. Generate the full pack. Existing valid outputs are skipped, so the command is resumable:

   ```bash
   GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --execute
   ```

7. Validate structure and hashes:

   ```bash
   python3 scripts/validate_resources.py
   ```

The script retries rate limits and server failures, writes each output atomically, converts with `ffmpeg`, validates with `ffprobe`, and updates `audio-index.json` after every successful clip.

## Editorial review

For every item in `asset-pack/audio/review.json`, a native speaker should set `status` to one of:

- `approved`: exact transcript, correct contemporary Israeli pronunciation and stress, clean beginning/end, consistent voice.
- `regenerate`: delivery problem but the content record is correct.
- `content_fix`: the pointed text, meaning, or expected pronunciation needs correction before regeneration.
- `rejected`: do not ship.

Review natural and teaching clips separately. “Teaching” should be slightly slower but still one connected word, never a distorted syllable-by-syllable performance.

## Cost expectation

Google currently states that Gemini 3.1 Flash TTS audio uses 25 tokens per second and charges $20 per million output audio tokens on the paid standard tier. That is about $0.0005 per generated second. A roughly six-minute final pack would therefore be around $0.18 in output audio, before auditions and regenerations. Treat this only as a planning estimate; preview pricing can change.

## Chirp 3 HD fallback

Google Cloud Chirp 3 HD is a sensible A/B fallback: it is GA, supports `he-IL`, can emit app formats through Cloud TTS, and supports pace control. Google currently notes that pause controls and custom pronunciations are unavailable for Hebrew, so it is not an automatic upgrade for this pack. If Gemini misses a specific clip after two prompt-stable regenerations, audition the matching `he-IL-Chirp3-HD-<voice>` clip and choose by native review. See Google Cloud's [Chirp 3 HD documentation](https://cloud.google.com/text-to-speech/docs/chirp3-hd).

## Source and licensing notes

- The rank list follows the verified OpenSubtitles 2018 FrequencyWords Hebrew snapshot in `asset-pack/data/frequency-2018-top100.tsv`, with the isolated source token `ג` excluded as an abbreviation/subtitle artifact. FrequencyWords labels generated content CC BY-SA 4.0; its [repository documents the source and license](https://github.com/hermitdave/FrequencyWords).
- Noto Sans Hebrew is bundled under the SIL Open Font License in `asset-pack/fonts/OFL.txt`; official Noto documentation confirms [Noto fonts use the OFL](https://github.com/notofonts/noto-docs/blob/main/docs/website/use.md).
- TTS clips and Hebrew editorial content still require product/legal review before public distribution. This guide is an engineering handoff, not legal advice.
