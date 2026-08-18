# Aleph Bet Kotlin Multiplatform architecture

Status: Working architecture decision

Last updated: 2026-08-18

## Purpose

This document describes the future structure of the Aleph Bet app and the order in which we will learn and build it.

The architecture should support an offline alphabet-learning release while staying small enough to understand as a first serious Kotlin Multiplatform project. It should create clean extension points for niqqud, words, and a future one-time purchase without implementing those features now.

## Product constraints that shape the code

- Android and iOS are first-class targets.
- The UI is shared with Compose Multiplatform.
- Curriculum content and reviewed audio ship inside the app.
- Learner progress is local and offline.
- V1 has no account, backend, analytics SDK, or cloud sync.
- The first release teaches letters; later content should reuse the learning machinery.
- The project is also a Kotlin/KMP learning vehicle, so important boundaries should be visible and explainable.

## Architecture at a glance

```text
Compose screen
    renders immutable UiState
    sends user actions
            |
            v
ViewModel
    coordinates one screen/session
            |
            v
Use cases and plain Kotlin learning logic
            |
            v
Repository interfaces
        /           \
       v             v
Bundled content     Room learner state
JSON + audio        progress + resume
```

Platform-specific services such as audio playback and database construction sit behind small common interfaces.

The central dependency rule is:

```text
presentation -> domain <- data
```

- Presentation depends on domain concepts and operations.
- Data implements domain repository contracts.
- Domain code does not import Compose, Room, Android, or iOS APIs.
- Platform code implements only the behavior that genuinely differs by operating system.

## Gradle modules and source sets

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

### `commonMain`

This is where most of Aleph Bet belongs:

- shared Compose UI and navigation;
- ViewModels and UI state;
- letter and lesson models;
- the lesson/session engine;
- progress rules;
- repository interfaces and shared implementations;
- Room entities, DAOs, and database declarations supported by Room KMP;
- bundled resource loading;
- Koin annotations and common DI configuration.

### `androidMain`

Keep only Android-specific work here:

- Android application and activity entry points;
- Android database path/builder details;
- Android audio implementation;
- permissions or lifecycle integrations that cannot be shared.

### `iosMain`

Keep only iOS-specific work here:

- the Compose root exposed to Swift;
- iOS database path/builder details;
- AVFoundation-backed audio implementation;
- iOS lifecycle or system integrations that cannot be shared.

### `iosApp`

This remains the Xcode application wrapper: app entry point, bundle configuration, signing, icons, and any small amount of Swift required to host the shared Compose UI.

### Why one Gradle module first

Multiple Gradle feature modules would enforce dependency boundaries, but they would also add build configuration and KMP source-set complexity before the app has working behavior.

Begin with package boundaries inside `composeApp`. Extract Gradle modules only when there is a measured reason, such as:

- compiler-enforced isolation is preventing real mistakes;
- build times have become painful;
- a component needs independent reuse;
- the feature set or team has grown enough to need stronger ownership.

## Future package map

This is a destination map, not a request to create every empty directory on day one.

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
      model/
      repository/
    data/
      content/
      repository/
    presentation/
      explorer/
      detail/

  learning/
    domain/
      model/
      engine/
      usecase/
    data/
      content/
      repository/
    presentation/
      home/
      lesson/
      completion/

  practice/
    domain/
    presentation/

  progress/
    domain/
      model/
      repository/
    data/
      local/
      repository/

  audio/
    domain/

  settings/
    data/
    presentation/
```

The corresponding platform implementations live under the same package names in `androidMain` and `iosMain` where practical. That makes the common contract and its platform implementations easy to find together.

## Feature responsibilities

### Alphabet

Owns factual letter content and the browsable reference experience.

Core concepts:

- `Letter`
- `LetterForm`
- `LetterSound`
- `AlphabetRepository`

Its data layer loads and maps bundled `alphabet.json`. Its presentation layer owns the alphabet explorer and letter detail UI.

Alphabet does not decide lesson order or learner mastery. Those belong to Learning and Progress.

### Learning

Owns authored lessons and the reusable session machinery that presents content, asks questions, evaluates answers, and advances through a session.

Core concepts:

- `Lesson`
- `LessonStep`
- `Exercise`
- `LessonSession`
- `LessonSessionEngine`

The engine should be plain Kotlin. Given a session and an action, it produces the next session state. That lets us test learning behavior without Compose, Room, Koin, or a device.

### Practice

Owns selection of previously introduced concepts for review.

It should reuse the same exercise and session engine as guided lessons. The difference is queue construction:

- Guided learning follows an authored lesson sequence.
- Practice builds a queue from progress and review rules.

Practice should not become a second, parallel lesson engine.

### Progress

Owns the meaning of learner progress and its durable storage.

Core concepts:

- `ConceptId`
- `ConceptType`
- `LearningStage`
- `ConceptProgress`
- `LessonProgress`
- `ProgressRepository`

Its data layer owns Room entities, DAOs, mappings, migrations, and the Room-backed repository.

Progress rules must distinguish factual records from claims. For example, `correctCount = 4` is factual; deciding that four answers make a letter `SOLID` is a product rule in the domain layer.

### Audio

Owns the shared playback contract and playback state needed by the learning experience.

The common layer should know operations such as play, stop, and observe playback state. It should not know AVFoundation or Android media-player types. Platform source sets supply those details.

### Settings

Owns small learner preferences such as audio behavior and completed onboarding. These values fit DataStore rather than Room unless relational queries become necessary.

## Data ownership

| Information | Source of truth | Why |
|---|---|---|
| Letter glyph, name, sounds, explanation | Bundled JSON/resources | Authored, immutable curriculum shipped with the app |
| Lesson sequence and steps | Bundled JSON/resources | Editorial content versioned with releases |
| Audio files | Compose resources | Reviewed offline media |
| Lesson completion | Room | Durable relational learner state |
| Per-letter progress | Room | Queried and updated across learning and practice |
| Practice attempts | Room, if retained | Durable evidence used by review rules and debugging |
| Audio preference and onboarding flag | DataStore | Small key-value preferences |
| Selected answer and current animation | ViewModel/Compose state | Temporary interaction state |
| Recoverable in-progress lesson checkpoint | Saved state and/or Room | Must survive the amount of interruption promised by the product |

Do not copy the full bundled curriculum into Room. A repository or use case joins immutable content with learner progress when a screen needs both.

## Model boundaries

Several objects can describe the same letter for different purposes. They should not collapse into one universal model.

### Content DTO

Matches the JSON schema and serialization needs. It may contain nullable or version-specific fields that are inconvenient elsewhere.

### Domain model

Represents a valid letter, lesson, exercise, or progress concept in business logic. Domain models should make invalid states difficult to construct.

### Room entity

Matches durable database storage and indexing needs. It changes when persistence changes.

### UI state

Contains exactly what a screen needs to render, already formatted or grouped for presentation. It can combine content and progress without exposing database entities to Compose.

Explicit mappings between these boundaries add a little code, but they prevent changes in JSON, Room, or UI layout from leaking through the entire app.

## Presentation pattern

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

Do not turn this into ceremony:

- A small reusable component usually needs parameters and callbacks, not its own ViewModel.
- A screen with two simple callbacks may not need a sealed action type.
- One-time effects should be modeled only when navigation, permission prompts, or messages genuinely need them.
- Navigation routes pass stable IDs such as `letterId` or `lessonId`, not whole domain objects.

The lesson ViewModel coordinates a session. It should not contain the lesson engine's rules. The engine remains a separately testable Kotlin object.

## Dependency injection: Koin Annotations

Use Koin Annotations rather than hand-written Koin module DSL.

Working conventions:

- Constructor injection is the default.
- `@Single` is for app-lifetime repositories, database objects, and stable services.
- `@Factory` is for inexpensive objects that should be created on demand.
- `@KoinViewModel` declares screen ViewModels.
- `@Module` creates explicit DI boundaries.
- `@ComponentScan` is limited to intentional packages rather than scanning the entire application indiscriminately.
- Annotated provider functions create external types such as the Room database that cannot be annotated directly.

Use the current Koin Compiler Plugin with `koin-annotations`. Do not adopt the deprecated `koin-ksp-compiler` setup from older tutorials.

Annotations remove binding boilerplate; they do not remove DI decisions. For every dependency we should still be able to explain:

- its lifetime;
- its interface and implementation;
- whether it is common or platform-specific;
- who owns cleanup;
- how a test supplies a fake.

Tests should normally construct the subject directly with fake dependencies. They should not start Koin merely to test domain behavior.

## Platform boundaries

Prefer an ordinary common interface with injected Android and iOS implementations. Use `expect`/`actual` only when the declaration itself truly must vary by target.

Likely platform boundaries in the alphabet release:

- database builder/path creation;
- audio playback;
- app lifecycle and audio interruption handling;
- platform-specific file or resource access not covered by a multiplatform library.

Do not create a generic `Platform` object containing unrelated operating-system services.

## Testing strategy

### `commonTest`

This should contain most high-value behavior tests:

- all bundled letters map into valid domain models;
- lesson definitions reference existing letters and audio;
- a lesson engine advances correctly after right and wrong answers;
- answer choices contain one correct answer and no duplicate concepts;
- final forms resolve to their base letters;
- progress rules produce the agreed learning stages;
- a practice queue favors the items required by the review policy;
- resuming a checkpoint produces a valid session.

### Database tests

Test DAO behavior, repository mappings, transactions, and every migration. A migration test matters more than a repository interface test that only repeats mock expectations.

### UI tests

Use focused Compose tests for behavior that is easiest to verify through semantics: correct RTL ordering, content descriptions, enabled states, answer feedback, and navigation entry points.

### Platform tests

Keep a small number of integration tests for database creation, audio playback/lifecycle behavior, and resource availability on both Android and iOS.

## React Native to KMP mental map

| React Native | Aleph Bet KMP equivalent |
|---|---|
| React component | `@Composable` function |
| Props | Function parameters |
| `useState` | `remember` / `rememberSaveable` for local UI state |
| Zustand/Redux screen store | ViewModel plus `StateFlow<UiState>` |
| Action/reducer | `UiAction` plus ViewModel or pure session transition |
| React Navigation | Typed Navigation Compose routes |
| AsyncStorage | DataStore for small preferences |
| SQLite ORM | Room KMP for durable relational progress |
| Bundled assets | Compose Multiplatform resources |
| Native module | Common interface with platform implementation |
| Jest unit test | `commonTest` with `kotlin.test` |

The most important difference is state lifetime. We will repeatedly classify state as Composable-local, ViewModel/session, saved state, Room, DataStore, or immutable bundled content.

## Build and learning sequence

The architecture grows through one three-letter vertical slice.

1. Create the KMP/Compose shell and run the same shared screen on Android and iOS.
2. Render one hard-coded letter to learn Composables, parameters, previews, and local state.
3. Load three letters from a bundled JSON resource and map DTOs into domain models.
4. Add the alphabet repository when the UI needs content without knowing where it came from.
5. Build and test a pure Kotlin lesson session before connecting it to Compose.
6. Expose the session through immutable UI state, actions, and a ViewModel.
7. Add Koin Annotations to assemble the real objects while tests keep using constructors.
8. Add Room when the slice first needs durable progress and resume behavior.
9. Add the common audio contract and Android/iOS implementations.
10. Verify process recreation, offline behavior, RTL, and accessibility.
11. Only then expand the same system to all 22 letters and five final forms.

At each step, we should be able to draw the data flow, identify the owner of every state value, and explain why each new abstraction exists.

## Decisions for the alphabet release

Chosen for now:

- shared Compose Multiplatform UI;
- one `composeApp` Gradle module plus `iosApp`;
- packages organized primarily by product feature;
- plain Kotlin lesson and progress logic;
- immutable UI state with unidirectional actions;
- Koin Annotations and the Koin Compiler Plugin;
- bundled JSON/audio as curriculum source of truth;
- Room for learner progress;
- DataStore for small preferences;
- narrow platform service boundaries.

Explicitly deferred:

- multiple Gradle feature modules;
- a generic framework for every future content type;
- backend and sync architecture;
- analytics architecture;
- purchase SDK and entitlement implementation;
- automatic pronunciation analysis;
- dependency abstractions without a current consumer.

## Open architecture questions

These should be answered by product decisions or the vertical slice:

1. How much of an interrupted lesson must survive process death?
2. Do we retain individual practice attempts or only progress aggregates?
3. Which audio library gives us reliable shared behavior, and what remains platform-specific?
4. Does the lesson engine need one general step model or a small sealed set of explicit step types?
5. When words arrive, do they fit the existing learning domain or justify a separate content module?
6. At what measured size or build cost would splitting Gradle modules help?

## Primary references

- [Kotlin Multiplatform project structure](https://kotlinlang.org/docs/multiplatform/multiplatform-discover-project.html)
- [Compose Multiplatform platform-specific behavior](https://kotlinlang.org/docs/multiplatform/compose-platform-specifics.html)
- [Android architecture and unidirectional data flow](https://developer.android.com/topic/architecture/ui-layer)
- [Koin Annotations for Kotlin Multiplatform](https://insert-koin.io/docs/reference/koin-annotations/kmp/)
- [Koin annotation definitions](https://insert-koin.io/docs/reference/koin-annotations/definitions/)
- [Koin modules and component scanning](https://insert-koin.io/docs/reference/koin-annotations/modules/)
