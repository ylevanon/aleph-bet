# Package map: Aleph Bet and CMPMemeCreator

Status: Working architecture explanation

Last updated: 2026-08-18

## Short answer

Aleph Bet should borrow CMPMemeCreator's broad shape, not copy its package tree.

Both projects start well with:

- one `composeApp` Gradle module;
- shared Compose UI in `commonMain`;
- small Android and iOS source sets;
- packages grouped primarily by product feature;
- ViewModels exposing observable screen state;
- common interfaces around platform-specific operations.

CMPMemeCreator is a useful example of Kotlin Multiplatform mechanics. Its exact packages reflect a small, mostly transient editing workflow. Aleph Bet needs boundaries for authored curriculum, reusable learning rules, durable learner progress, and practice selection. Those responsibilities do not exist in the meme app, so its tree cannot be our finished architecture.

This comparison references [CMPMemeCreator at commit `9c020a2`](https://github.com/philipplackner/CMPMemeCreator/tree/9c020a2687b508204c8a7084826dc7d3f3a07a18).

## Three structures that are easy to confuse

### Gradle modules

A Gradle module is a compilation and dependency boundary. Both projects initially use one shared application module:

```text
:composeApp
iosApp/        native Xcode wrapper, not another shared Gradle feature module
```

Aleph Bet packages such as `alphabet` and `learning` are not separate Gradle modules in V1. They are organization and dependency conventions inside `composeApp`.

### Kotlin source sets

Source sets answer **which platforms can compile this code?**

```text
commonMain   Android and iOS shared code
androidMain  Android-only implementations and entry points
iosMain      iOS-only implementations and entry points
```

The same logical feature can have files in several source sets. For example, the common `AudioPlayer` contract can live under `commonMain/audio`, while its Android and iOS implementations use matching `audio` packages in their platform source sets.

### Kotlin packages

Packages answer **which product responsibility owns this code?** The Aleph Bet map is feature-first, then layered inside a feature only where the distinction is useful:

```text
alphabet/domain
alphabet/data
alphabet/presentation
```

This is similar to organizing a React Native app by feature and then keeping that feature's API, state, and UI close together. It is not the equivalent of publishing a separate npm package for every feature.

## Side-by-side comparison

| Concern | CMPMemeCreator | Aleph Bet | Why Aleph Bet differs |
|---|---|---|---|
| Gradle modules | One `composeApp` | One `composeApp` | We agree with the sample: extra modules would be premature. |
| Top-level features | `meme_editor`, `meme_gallery` | `alphabet`, `learning`, `practice`, `progress`, `settings` | Our product has several durable responsibilities rather than one dominant editor. |
| Shared application code | `core/presentation`, `di`, root files | `app/navigation`, `app/di`, `design` | We name the reason for sharing and avoid an unrestricted `core` bucket. |
| Domain layer | Export and storage contracts under `meme_editor/domain` | Letter models, session engine, progress rules, and repository contracts under their owning features | Learning behavior must remain independent of UI and persistence. |
| Data layer | Platform meme exporter and cache strategy | Bundled curriculum repositories, Room progress repositories, DataStore settings | Aleph Bet joins immutable app content with durable learner state. |
| ViewModel role | Much editor behavior lives directly in `MemeEditorViewModel` | ViewModels coordinate plain Kotlin engines and repositories | Queue construction, answer evaluation, and learning-stage transitions need deterministic tests. |
| Dependency injection | Hand-written Koin DSL plus an `expect`/`actual` platform module | Koin Annotations, constructor injection, annotated platform providers where needed | This follows our preference and removes repetitive binding DSL. |
| Persistence | Export/cache behavior; no relational learner history | Room for lesson and concept progress | App relaunches and updates must preserve learning evidence. |
| Runtime content | Bundled meme templates | Bundled alphabet and authored lessons plus learner-specific progress | Curriculum and learner state have different sources of truth. |
| Platform boundaries | Exporting, cache storage, sharing, pixel conversion | Audio playback, database construction, lifecycle integration | Platform abstractions should follow actual OS differences in each product. |

## What CMPMemeCreator is doing

Its common package tree is approximately:

```text
cmp_memecreator/
  core/
    presentation/
    theme/
  di/
  meme_editor/
    data/
    domain/
    presentation/
  meme_gallery/
    presentation/
```

This is reasonable for that app:

- the gallery selects a bundled template;
- the editor owns most interaction state;
- domain interfaces isolate exporting and storage behavior;
- Android and iOS provide platform implementations;
- navigation connects two destinations;
- there is no lesson curriculum, review policy, or relational progress model.

The sample also demonstrates useful patterns we should retain: typed navigation, `UiState` plus actions, common interfaces for platform behavior, and keeping most Compose code shared.

## The Aleph Bet package map

Our expected V1 destination is:

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

This is a destination map, not a command to create empty directories. A package appears when it has a real class or function to own.

## Why each Aleph Bet package exists

### `app`

Owns application assembly: the Compose root, navigation graph, and DI startup. It may know which features exist, but features should not depend on `app`.

This replaces the vague part of a typical `core` package with an explicit composition root.

### `design`

Owns genuinely reusable visual language: theme, typography, spacing, and components used by multiple features. A component used by only one screen stays with that feature until reuse is real.

`design` must not become a home for domain models, repositories, or miscellaneous helpers.

### `alphabet`

Owns facts about letters and the reference experience:

- `Letter`, `LetterForm`, and `LetterSound`;
- loading and validating `alphabet.json`;
- `AlphabetRepository`;
- alphabet explorer and letter-detail presentation.

It does not own lesson order or decide whether the learner is doing well.

### `learning`

Owns authored lesson definitions and the reusable session state machine:

- `Lesson`, `LessonStep`, and exercise types;
- `SessionPlan` and session transitions;
- answer evaluation;
- lesson-session presentation.

This is deliberately outside a ViewModel because it is the product's most important testable behavior.

### `practice`

Owns the policy that chooses what to review. It reads introduced concepts and progress, constructs a practice queue, and then hands that queue to Learning's session machinery.

It does not duplicate exercises or session transitions.

### `progress`

Owns the meaning and persistence of learner history:

- concept and lesson progress;
- learning-stage transitions;
- Room entities, DAOs, and mappings;
- attempt history and resumable lesson checkpoints.

Keeping this separate prevents Room entities from becoming alphabet or UI models.

### `audio`

Owns the small common playback contract. Android and iOS implementations live in their corresponding source sets under a matching package.

Audio is cross-feature infrastructure with a specific name and responsibility. That is clearer than placing it in `core` or `util`.

### `settings`

Owns small app preferences and the Settings/About destination. It can use DataStore without making DataStore a general repository for progress.

## Intended dependency direction

Packages should depend on the smallest public concepts they need:

```text
app ---------> feature presentation
                    |
                    v
alphabet <------ learning <------ practice
   domain          domain          domain
                       \             /
                        v           v
                       progress domain

data implementations ------> domain contracts
platform implementations --> common platform contracts
```

This drawing is directional, not a demand for a separate interface around every class. The important rules are:

- domain code does not import Compose, Room, Koin, Android, or iOS;
- presentation does not receive Room entities or content DTOs;
- `app` assembles features, while features do not reach back into `app`;
- Practice can reuse Learning's public session concepts, but not its presentation internals;
- a shared concept has one clear owner rather than being moved to `core` automatically.

Because V1 uses one Gradle module, package boundaries rely on discipline and tests rather than compiler enforcement. That tradeoff is intentional while one developer is learning and shipping the first slice.

## Koin difference

CMPMemeCreator declares its bindings with Koin's module DSL and uses an `expect`/`actual` `platformAppModule`. Aleph Bet will use Koin Annotations and the compiler plugin:

- constructor injection by default;
- `@KoinViewModel` for route-level ViewModels;
- `@Single` for stable repositories and services;
- narrow annotated provider functions for external or platform-created objects;
- direct construction with fakes in unit tests.

This changes graph declaration, not dependency direction. Koin should assemble `LessonViewModel`; it should not hide how a session engine, repository, or audio player depends on another object.

## What we deliberately do not copy

### A broad `core` package

`core` often becomes the place for anything that does not yet have an owner. Aleph Bet uses names such as `app`, `design`, and `audio` so the ownership rule remains visible. If code is shared by two features, that alone does not make it core; first ask which feature conceptually owns it.

### Product rules in a large ViewModel

Direct state mutation is appropriate for much of a visual meme editor. Our lesson rules update durable progress and are reused by guided lessons and practice. They deserve a plain Kotlin session engine rather than a growing `when(action)` block in `LessonViewModel`.

### Platform abstractions copied by name

Aleph Bet does not need meme export, cache storage, a share sheet, or pixel-conversion abstractions merely because the sample has them. We create common contracts only for our real seams, especially audio and database construction.

### Layers in every feature

`practice` has no data package unless it eventually owns a data source. `progress` needs no presentation package until it has its own destination. A uniform tree full of empty `data/domain/presentation` directories would communicate less, not more.

## When this map should evolve

Keep the feature packages inside one `composeApp` through the three-letter vertical slice and the alphabet release. Consider extracting Gradle modules only when evidence appears:

- unrelated code repeatedly recompiles and profiling shows a useful module boundary;
- developers repeatedly violate an important dependency rule;
- a feature needs independent reuse or replacement;
- multiple owners need stable APIs and isolated builds;
- tests or platform variants become materially difficult to manage in one module.

The likely future module boundary is not automatically one module per current package. For example, a stable learning engine might eventually become a reusable module, while small presentation and settings packages could remain in the app module.

## React Native translation

| React Native idea | KMP meaning here |
|---|---|
| One app workspace/package | One `composeApp` Gradle module |
| `src/features/alphabet` | `alphabet` Kotlin package in `commonMain` |
| Native iOS/Android implementation | Matching code in `iosMain` and `androidMain` |
| Zustand/Redux screen store | ViewModel exposing `StateFlow<UiState>` |
| Pure reducer/domain function | Plain Kotlin session transition |
| SQLite adapter | Room repository implementation under `progress/data` |
| Context/provider wiring | Koin application assembly |
| Shared component library folder | `design`, but only for proven reuse |

The main Kotlin-specific distinction is that folders, packages, source sets, and Gradle modules are separate concepts. We are using packages for product organization now and reserving Gradle modules for boundaries that later earn their build complexity.

## Practical rule while building

For each new class, ask in this order:

1. Which product responsibility owns it?
2. Is it domain logic, data translation/persistence, or presentation?
3. Can it compile in `commonMain`, or does it genuinely require a platform source set?
4. Does it need dependency injection, or can its caller construct it directly?
5. Are we creating a package because code exists, or creating empty architecture in anticipation?

Those questions will keep the package map useful as a teaching tool instead of turning it into a template we obey without understanding.
