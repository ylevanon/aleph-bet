# Aleph Bet

This repository currently contains the product research, V1 specification, and a production-ready Hebrew asset pack for the planned Kotlin/Compose Multiplatform app.

## Product and architecture roadmap

- [Personalized Kotlin Multiplatform course](docs/course/README.md)
- [Alphabet V1 roadmap](docs/product/alphabet-v1-roadmap.md)
- [Aleph Bet Kotlin Multiplatform architecture](docs/architecture/README.md)
- [React Native to KMP architecture guide](docs/architecture/react-native-to-kmp-architecture.md)
- [Architecture answers and decision queue](docs/architecture/answers.md)
- [Documentation index](docs/README.md)

## What is ready

- 27 block-letter forms: 22 letters plus 5 final forms.
- 16 practical Modern Hebrew niqqud/consonant marks.
- Exactly 100 directly verified OpenSubtitles 2018 Hebrew lexical forms, with source ranks/counts and 112 pointed pronunciations where unpointed spellings are ambiguous.
- 312 deterministic Gemini TTS request definitions: natural and teaching-speed words, letter names and sounds, and niqqud names and examples.
- 180 generated foundation clips for the two reviewed voice candidates; remaining word audio can be generated from the committed request plan.
- Four voice-audition requests.
- Noto Sans Hebrew variable font and its OFL license.
- A modern light/dark visual token system plus SVG previews.
- Offline validation and resumable audio generation scripts.

Generated foundation audio is committed under `asset-pack/audio/generated/`. The complete request bodies for rebuilding clips and producing the remaining word audio are materialized in [`asset-pack/audio/jobs.json`](asset-pack/audio/jobs.json).

## Quick start

```bash
python3 scripts/validate_resources.py
python3 scripts/generate_tts.py                 # rebuild the offline 312-call plan
python3 scripts/generate_tts.py --auditions     # rebuild the 4-call voice plan
python3 scripts/stage_kmp_resources.py path/to/commonMain/composeResources
```

After creating a Gemini API key, keep it outside the repository and run:

```bash
GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --auditions --execute
GEMINI_API_KEY='your-key' python3 scripts/generate_tts.py --execute
```

See [`docs/assets/audio-production.md`](docs/assets/audio-production.md) for voice selection, review, cost, retry, and KMP packaging guidance.

## Asset layout

```text
asset-pack/
  data/          Hebrew alphabet, niqqud, and frequency-word JSON
  audio/         TTS config, exact calls, review state, and generated clips
  fonts/         Noto Sans Hebrew and OFL license
  visual/        design tokens and SVG reference assets
scripts/         generation and validation tools
```

Letters and niqqud are intentionally not exported as one image per glyph. The app should render Unicode Hebrew with the bundled font so text remains sharp, accessible, themeable, and correctly shaped at every density.
