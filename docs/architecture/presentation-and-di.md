# Presentation and dependency injection

Status: Working architecture decision

Last updated: 2026-08-18

## Presentation flow

Use unidirectional data flow for behavior-rich destinations:

```text
UiState -> Screen -> UiAction -> ViewModel -> new UiState
```

A typical screen area owns:

- an immutable `UiState`;
- a sealed `UiAction` when the action set is meaningful;
- a ViewModel exposing `StateFlow<UiState>`;
- a route-level Composable that obtains the ViewModel;
- a stateless screen Composable that receives state and callbacks.

## Responsibility split

### Screen Composable

- Renders state.
- Sends user actions.
- Owns only short-lived visual state that does not affect product behavior.

### ViewModel

- Coordinates one navigation destination or session.
- Combines repository/domain data into UI state.
- Responds to user actions.
- Survives configuration changes.

### Domain engine or use case

- Owns lesson progression, answer evaluation, queue construction, and progress rules.
- Remains plain Kotlin and independently testable.

The lesson ViewModel coordinates a `LessonSession`; it does not become the lesson engine itself.

## Avoid presentation ceremony

- Reusable components usually need parameters and callbacks, not ViewModels.
- A simple screen may not need a sealed action type.
- Add one-time effect machinery only for real navigation, permission, or message behavior.
- Routes pass stable identifiers such as `letterId` and `lessonId`, not entire domain objects.
- Audio playback progress can remain local when it is purely visual; session-relevant playback state belongs in the ViewModel or audio service.

## State lifetimes

| Lifetime | Examples |
|---|---|
| Compose-local | pressed state, expanded controls, animation progress |
| ViewModel/session | current exercise, shuffled choices, selected answer, feedback |
| Saved state | minimum checkpoint needed after process recreation |
| Room | completed lessons, progress counts, last-practiced timestamps |
| DataStore | onboarding and audio preferences |
| Bundled content | glyphs, explanations, lesson definitions, audio paths |

State lifetime is a recurring design exercise: identify the owner before choosing the API.

## Koin Annotations decision

Use Koin Annotations instead of hand-written Koin module DSL.

Conventions:

- Constructor injection is the default.
- `@Single` is for app-lifetime repositories, database objects, and stable services.
- `@Factory` is for inexpensive objects created on demand.
- `@KoinViewModel` declares screen ViewModels.
- `@Module` creates explicit DI boundaries.
- `@ComponentScan` is limited to intentional packages.
- Annotated provider functions construct external types such as Room databases.

Use the current Koin Compiler Plugin with `koin-annotations`. Do not copy the deprecated `koin-ksp-compiler` setup from older tutorials.

Annotations remove binding boilerplate, not architecture decisions. For every dependency, be able to explain:

- its lifetime;
- its interface and implementation;
- whether it is common or platform-specific;
- who owns cleanup;
- how a test replaces it.

Tests should normally instantiate the subject directly with fake dependencies rather than starting Koin.

## React Native to KMP map

| React Native | Aleph Bet KMP equivalent |
|---|---|
| React component | `@Composable` function |
| Props | Function parameters |
| `useState` | `remember` / `rememberSaveable` for local UI state |
| Zustand/Redux screen store | ViewModel plus `StateFlow<UiState>` |
| Action/reducer | `UiAction` plus ViewModel or pure session transition |
| React Navigation | Typed Navigation Compose routes |
| AsyncStorage | DataStore for small preferences |
| SQLite ORM | Room KMP for relational learner state |
| Bundled assets | Compose Multiplatform resources |
| Native module | Common interface with platform implementation |
| Jest unit test | `commonTest` with `kotlin.test` |

## Primary references

- [Android UI architecture and unidirectional data flow](https://developer.android.com/topic/architecture/ui-layer)
- [Koin Annotations for Kotlin Multiplatform](https://insert-koin.io/docs/reference/koin-annotations/kmp/)
- [Koin annotation definitions](https://insert-koin.io/docs/reference/koin-annotations/definitions/)
- [Koin modules and component scanning](https://insert-koin.io/docs/reference/koin-annotations/modules/)
