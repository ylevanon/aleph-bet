# Hebrew asset pack

The pack is intentionally independent of a specific KMP module name. When the Compose Multiplatform project exists, copy or sync resources using this mapping:

| Source | Suggested Compose resource destination | Purpose |
|---|---|---|
| `data/*.json` | `shared/src/commonMain/composeResources/files/hebrew/` | Curriculum/content data |
| `audio/generated/<voice>/` | `shared/src/commonMain/composeResources/files/audio/he/` | Offline app audio |
| `fonts/noto_sans_hebrew.ttf` | `shared/src/commonMain/composeResources/font/` | Correct Hebrew and niqqud rendering |
| `visual/design-tokens.json` | Convert to Kotlin theme tokens | Color, type, shape, spacing, motion |
| `visual/svg/app-mark.svg` | Platform icon source only | Brand mark source |

The three card SVGs are visual references, not runtime cards. Rebuild them as Compose components and render the Hebrew strings as text.

## Content semantics

- `alphabet.json` includes all block forms, names, common Modern Israeli sounds, and short sound samples.
- `niqqud.json` is practical Modern Hebrew, including rare reduced vowels and essential consonant dots, but excludes cantillation.
- `words.json` preserves the top 100 lexical surface forms from the verified OpenSubtitles 2018 snapshot, with source ranks and counts. The isolated token `ג` is excluded as an abbreviation/subtitle artifact. A surface spelling may have multiple `senses`; each has its own pointed form and audio identity.
- Frequency rank is not lesson order. A later curriculum pass should sequence these items by teachability and usefulness.

Every generated clip starts in `pending` state in `audio/review.json`. Only native-speaker-approved clips should ship.

When the KMP module exists, stage the pack into it without copying production-only prompts or review metadata:

```bash
python3 scripts/stage_kmp_resources.py \
  path/to/shared/src/commonMain/composeResources
```

Add `--require-audio` in release automation. It fails until all 312 catalogued MP3s exist and writes a hashed `resource_pack_manifest.json` for the staged files.
