# Project structure and platform boundaries

Status: Working architecture decision

Last updated: 2026-08-18

## Initial module structure

Start with one shared application module and the native iOS wrapper:

```text
composeApp/
  src/
    commonMain/
    commonTest/
    androidMain/
    androidUnitTest/
    iosMain/
    iosTest/

iosApp/
```

This keeps the first project understandable while still teaching the defining KMP concept: shared source sets can only use APIs available to all their targets.

## Source-set responsibilities

### `commonMain`

Most of Aleph Bet belongs here:

- shared Compose UI and navigation;
- ViewModels and immutable UI state;
- letter, lesson, exercise, and progress models;
- the lesson/session engine;
- repositories and use cases;
- bundled-resource loading;
- Room declarations supported by Room KMP;
- Koin annotations and common dependency wiring.

### `androidMain`

Keep only Android-specific work here:

- Android application and activity entry points;
- Android database path and builder details;
- Android audio implementation;
- Android lifecycle or permission integrations that cannot be shared.

### `iosMain`

Keep only iOS-specific work here:

- the Compose root exposed to Swift;
- iOS database path and builder details;
- AVFoundation-backed audio implementation;
- iOS lifecycle integrations that cannot be shared.

### `iosApp`

The Xcode wrapper owns the native app entry point, bundle configuration, signing, icons, and the small amount of Swift required to host shared Compose UI.

## Package map

For a detailed comparison with the reference project, see [Package map: Aleph Bet and CMPMemeCreator](package-map-comparison.md).

This is the expected destination, not a request to scaffold empty packages before they have responsibilities.

```text
composeApp/src/commonMain/kotlin/.../
  app/
    App.kt
    di/
    navigation/

  design/
    theme/
    components/

  alphabet/
    domain/
    data/
    presentation/

  learning/
    domain/
    data/
    presentation/

  practice/
    domain/
    presentation/

  progress/
    domain/
    data/

  audio/
    domain/

  settings/
    data/
    presentation/
```

Platform implementations should use matching package names in `androidMain` and `iosMain` where practical, making a common contract and its target implementations easy to find together.

## One module first

Package boundaries are enough for the first vertical slice. Multiple Gradle feature modules would add KMP configuration and dependency management before the app has working behavior.

Extract modules only when there is evidence that they help:

- compiler-enforced isolation would prevent recurring mistakes;
- build times have become painful;
- code genuinely needs independent reuse;
- feature ownership has grown beyond one developer;
- the module has a stable public API worth protecting.

## Platform-service rule

Prefer an ordinary common interface with injected Android and iOS implementations. Use `expect`/`actual` only when the declaration itself must differ by target.

Likely platform boundaries in the alphabet release:

- database path and builder creation;
- audio playback and audio interruptions;
- app lifecycle integration;
- resource or file access not covered by a multiplatform library.

Avoid a generic `Platform` service containing unrelated operating-system behavior.

## Compose resources

The shared resource tree will contain:

```text
composeApp/src/commonMain/composeResources/
  files/hebrew/
  files/audio/he/
  font/
  values/
```

Hebrew glyphs remain Unicode text rendered with the bundled font. SVG previews are design references, not runtime letter cards.

## Primary references

- [Kotlin Multiplatform project structure](https://kotlinlang.org/docs/multiplatform/multiplatform-discover-project.html)
- [Compose Multiplatform platform-specific behavior](https://kotlinlang.org/docs/multiplatform/compose-platform-specifics.html)
