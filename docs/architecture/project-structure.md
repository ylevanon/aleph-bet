# Project structure and platform boundaries

Status: Confirmed initial scaffold

Last updated: 2026-08-19

## Initial module structure

Start from the current official Android-and-iOS shared-UI generator shape:

```text
shared/
  src/
    commonMain/
    commonTest/
    androidMain/
    androidHostTest/
    iosMain/
    iosTest/

androidApp/
iosApp/
```

`shared` contains common code/UI plus Kotlin platform implementations. `androidApp` contains the Android application entry point. `iosApp` is the native Xcode host. This current separation also avoids tying the project to the older layout where Android entry points lived inside a single `composeApp` module.

The generated project confirmed these names on 2026-08-19. The Kotlin package root is `com.ylevanon.alephbet`, `settings.gradle.kts` includes `:shared` and `:androidApp`, and the Xcode host consumes a static framework named `Shared`.

## Source-set responsibilities

### `shared/src/commonMain`

Most of Aleph Bet belongs here:

- shared Compose UI and navigation;
- ViewModels and immutable UI state;
- letter, lesson, exercise, and progress models;
- the lesson/session engine;
- repositories and use cases;
- bundled-resource loading;
- Room declarations supported by Room KMP;
- Koin annotations and common dependency wiring.

### `shared/src/androidMain`

Keep only Android-specific work here:

- Android database path and builder details;
- Android audio implementation;
- Android lifecycle or permission integrations that cannot be shared.

### `androidApp`

The Android application module owns the application plugin/configuration, manifest, and `Activity` that hosts the common root Composable. It depends on `shared`; feature/domain code does not depend on it.

### `shared/src/iosMain`

Keep only iOS-specific work here:

- the Compose root exposed to Swift;
- iOS database path and builder details;
- AVFoundation-backed audio implementation;
- iOS lifecycle integrations that cannot be shared.

### Test source sets

- `commonTest` contains platform-independent tests written against common APIs and `kotlin.test`.
- `androidHostTest` contains Android-target host tests that run locally without installing the application on a device.
- `iosTest` contains tests compiled in the iOS target context.

### `iosApp`

The Xcode wrapper owns the native app entry point, bundle configuration, signing, icons, and the small amount of Swift required to host shared Compose UI.

## Package map

For a detailed comparison with the reference project, see [Package map: Aleph Bet and CMPMemeCreator](package-map-comparison.md).

This is the expected destination, not a request to scaffold empty packages before they have responsibilities.

```text
shared/src/commonMain/kotlin/.../
  app/
    App.kt
    di/
    navigation/

  design/
    theme/
    components/

  home/
    presentation/

  onboarding/
    presentation/

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

## One shared-code module first

The thin Android application entry module is a platform host, not a feature split. Package boundaries inside `shared` are enough for the first vertical slice. Additional Gradle feature modules would add KMP configuration and dependency management before the app has working behavior.

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
shared/src/commonMain/composeResources/
  files/hebrew/
  files/audio/he/
  font/
  values/
```

Hebrew glyphs remain Unicode text rendered with the bundled font. SVG previews are design references, not runtime letter cards.

## Primary references

- [Kotlin Multiplatform project structure](https://kotlinlang.org/docs/multiplatform/multiplatform-discover-project.html)
- [Compose Multiplatform platform-specific behavior](https://kotlinlang.org/docs/multiplatform/compose-platform-specifics.html)
