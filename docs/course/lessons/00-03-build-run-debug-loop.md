# Lesson 00.03 — The fast shared-UI development loop

Status: Passed

Date passed: 2026-08-19

Module: 00 — Orientation and toolchain

## Product outcome

The generated shared starter runs on Android and iOS. A development-only desktop target renders the same shared `App()` with Compose Hot Reload, so ordinary shared-UI changes do not require rebuilding a phone target after every save.

## Why this lesson changed

The original lesson overemphasized Gradle stages, task discovery, deliberate compiler failures, and debugger mechanics before the learner had made a meaningful product change. That sequence produced configuration fatigue instead of useful Kotlin practice.

Build internals remain relevant, but they will now be introduced just in time when a real dependency or failure requires them. This lesson establishes the feedback loop needed for product-first instruction.

## Learning outcomes

By the end, the learner can:

- trace Android and iOS from their native entry points into the shared `App()`;
- explain the Swift-to-Kotlin Compose bridge at a useful level;
- distinguish a Compose preview, Desktop Hot Reload, and a real platform run;
- explain why one `commonMain` composable is compiled separately for Android, iOS, and desktop;
- use desktop as the fast shared-UI host and Android/iOS as platform-verification targets.

## The three execution environments

| Environment | What runs | Primary purpose |
|---|---|---|
| Compose Preview | One annotated Composable inside IDE preview tooling | Isolated components, screen states, themes, and layout variations |
| Desktop Hot Reload | A real JVM Compose window running shared code | Rapid shared UI, navigation, state, and common-logic iteration |
| Android/iOS run | The packaged platform application and its native host | Platform integration, fonts, safe areas, gestures, services, and release behavior |

A preview is not the complete application. Desktop is a real Compose application, but it is not an Android emulator or iOS simulator. Platform verification remains required.

## Entry-point trace

### Android

```text
Android
  -> MainActivity.onCreate()
  -> setContent { App() }
  -> shared App()
```

`setContent` is the boundary where the Android Activity begins rendering Compose.

### iOS

```text
SwiftUI iOSApp
  -> ContentView
  -> UIViewControllerRepresentable
  -> MainViewControllerKt.MainViewController()
  -> ComposeUIViewController { App() }
  -> shared App()
```

`ContentView.swift` is the native side of the Swift/Kotlin call. `MainViewController.kt` is the Kotlin iOS adapter. It returns a normal `UIViewController` that hosts a Composable lambda.

```kotlin
fun MainViewController() = ComposeUIViewController { App() }
```

`ComposeUIViewController` is a regular function returning `UIViewController`. Its `content` argument has the function type `@Composable () -> Unit`; `{ App() }` is the composable lambda supplied to it.

### Desktop development host

```text
JVM main()
  -> application { ... }
  -> Window { App() }
  -> shared App()
```

The desktop `Window` is itself part of Compose Desktop. It renders a third compilation of the shared UI rather than mirroring either phone application.

## Actual lab evidence

### Platform baselines

- The Android application launched and the generated button revealed its hidden content.
- The iOS application launched and matched the shared behavior.
- Editing `App.kt` did not mutate either already-running native binary; each target required another deployment or incremental-apply action.
- The learner correctly predicted that a common text change affects both phone targets after they are rebuilt.
- The learner correctly explained why Android-only APIs cannot be placed directly in `commonMain`.

### Development-loop correction

The project was initially generated with Android and iOS only. Current Kotlin Multiplatform guidance recommends a desktop target even when desktop will not ship, because the JVM target supplies Compose Hot Reload.

The development harness added:

- a JVM target named `desktop`;
- the current-OS Compose Desktop runtime;
- `desktopMain` with a small `main()` window;
- a desktop `actual` implementation for the starter's expected platform function;
- compatible JetBrains Runtime provisioning for Hot Reload.

The desktop compilation and `:shared:hotRunDesktop` task were verified successfully.

## Working policy for later lessons

```text
Write shared UI or common behavior
          -> save and observe through Desktop Hot Reload
          -> use focused common tests for product rules
          -> verify Android at a meaningful checkpoint
          -> verify iOS at a meaningful checkpoint
```

Use a phone target immediately when the work depends on its actual environment, including:

- safe areas, system bars, fonts, and text rendering;
- navigation gestures and lifecycle;
- audio, permissions, database paths, and other platform services;
- accessibility behavior;
- release and store validation.

The desktop harness is development tooling, not a promised V1 platform.

## React Native comparison

React Native keeps a JavaScript runtime inside the native shell, so Metro can replace much application code without rebuilding the shell. KMP compiles shared Kotlin into each target's output. Desktop Hot Reload provides a fast JVM development host, while Android and especially Kotlin/Native iOS still have distinct build and deployment loops.

This is a real developer-experience tradeoff, not evidence that production KMP applications cannot exist.

## Teach-back evidence

The learner explained:

> The window is a Composable itself. It is rendering the shared UI in a new target.

That captures the durable model: shared source is not one remote process serving several platforms; it participates in separate platform compilations.

## Completion evidence

- [x] Android starter runs.
- [x] iOS starter runs.
- [x] Both native entry paths are traced into shared `App()`.
- [x] Preview, Hot Reload, and target runs are distinguished.
- [x] Desktop development target compiles.
- [x] Compose Hot Reload task configures successfully.
- [x] Learner explains the desktop window as another target rendering shared UI.
- [x] Application source remains unpushed while the configured GitHub remote is public.
- [x] Public progress record is updated.

## Next retrieval

Lesson 01.01 starts direct Kotlin practice through alphabet-domain examples. Gradle returns only when a real dependency or build problem creates a reason to learn it.

## References

- [Kotlin Multiplatform quickstart](https://kotlinlang.org/docs/multiplatform/quickstart.html)
- [Compose Hot Reload](https://github.com/JetBrains/compose-hot-reload)
- [Compose Multiplatform previews](https://kotlinlang.org/docs/multiplatform/compose-previews.html)
- [Compose Multiplatform platform specifics](https://kotlinlang.org/docs/multiplatform/compose-platform-specifics.html)
