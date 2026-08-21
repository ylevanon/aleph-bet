# Package map: Aleph Bet and CMPMemeCreator

Status: Working architecture explanation

Last updated: 2026-08-19

## Short answer

Aleph Bet should borrow CMPMemeCreator's broad shape, not copy its package tree.

Both projects start well with:

- one shared-code Gradle module rather than a Gradle module per feature;
- shared Compose UI in `commonMain`;
- small Android and iOS source sets plus a thin desktop development harness;
- packages grouped by clear ownership, with product features as the default;
- ViewModels exposing observable screen state;
- common interfaces around platform-specific operations.

CMPMemeCreator is a useful example of Kotlin Multiplatform mechanics. Its exact packages reflect a small, mostly transient editing workflow. Aleph Bet needs boundaries for authored curriculum, reusable learning rules, durable learner progress, and practice selection. Those responsibilities do not exist in the meme app, so its tree cannot be our finished architecture.

This comparison references [CMPMemeCreator at commit `9c020a2`](https://github.com/philipplackner/CMPMemeCreator/tree/9c020a2687b508204c8a7084826dc7d3f3a07a18).

## Three structures that are easy to confuse

### Gradle modules

A Gradle module is a compilation and dependency boundary. The referenced CMPMemeCreator snapshot uses the older combined Android/shared module shape:

```text
:composeApp
iosApp/        native Xcode wrapper, not another shared Gradle feature module
```

The current official starter shape we expect for Aleph Bet separates entry points:

```text
:shared        common UI/domain/data plus Kotlin platform implementations
:androidApp    thin Android application entry module
iosApp/        native Xcode wrapper
```

The exact generated names are confirmed in Lesson 00.02. Aleph Bet packages such as `alphabet` and `learning` are not separate Gradle modules in V1. They are organization and dependency conventions inside the shared-code module.

### Kotlin source sets

Source sets answer **which platforms can compile this code?**

```text
commonMain   code shared by every configured target
androidMain  Android-only implementations and entry points
iosMain      iOS-only implementations and entry points
desktopMain  development-only JVM host and substitutes for Hot Reload
```

The same logical feature can have files in several source sets. For example, the common `AudioPlayer` contract can live under `commonMain/audio`, while its Android and iOS implementations use matching `audio` packages in their platform source sets. A desktop implementation may exist only to support the development harness; it does not expand the V1 product platform list.

### Kotlin packages

Packages answer **which product responsibility owns this code?** The Aleph Bet map is feature-first, then layered inside a feature only where the distinction is useful:

```text
alphabet/domain
alphabet/data
alphabet/presentation
```

This is similar to organizing a React Native app by feature and then keeping that feature's API, state, and UI close together. It is not the equivalent of publishing a separate npm package for every feature.

Unlike Expo Router, a Kotlin package does not create navigation. Unlike a Gradle module, it does not create a separately compiled artifact. A Kotlin file declares its package at the top and other files import declarations by that qualified name:

```kotlin
package com.ylevanon.alephbet.alphabet.domain

data class LetterId(val value: String)
```

```kotlin
import com.ylevanon.alephbet.alphabet.domain.LetterId
```

The directory should match the package because tools and humans expect it, although the Kotlin compiler primarily follows the `package` declaration. Matching packages may also exist in `commonMain`, `androidMain`, and `iosMain`; the source set determines the target, while the package keeps related contracts and implementations discoverable.

Packages inside one Gradle module are not hard security walls. Kotlin has no Java-style package-private visibility, and `internal` means visible throughout the whole Gradle module. Until we extract modules, dependency direction is maintained by naming, code review, tests, and restraint. That is another reason to keep the map simple enough to understand.

## Reading the map through Bearywell

Bearywell uses a pragmatic React Native organization:

```text
app/          Expo Router destinations and layouts
components/   reusable UI plus some feature-sized UI
lib/          pure product rules, hooks, adapters, and integrations
context/      application-level providers
utils/        small technical helpers
```

That structure makes route ownership extremely visible, but `components` and especially `lib` contain code with several different reasons to change. This is normal in a React Native app and is productive while the ownership remains understandable.

The Kotlin map makes some of those ownership boundaries explicit because ViewModels, Room, source sets, and dependency injection create more architectural roles than a typical route file exposes.

| Bearywell example | Responsibility | Approximate Aleph Bet home |
|---|---|---|
| `app/(tabs)/index.tsx` | Route-level screen and data coordination | `home/presentation` or the relevant feature's `presentation` package |
| `components/screen.tsx`, `components/button.tsx` | Reusable visual primitives | `design/components` |
| `components/day-ring.tsx` | UI strongly tied to monitoring concepts | The owning feature's `presentation/components`, not automatically `design` |
| `lib/person-status.ts` | Pure product decision logic used by multiple surfaces | The owning feature's `domain` package |
| `lib/onboarding-flow.ts` | Flow rules and route progress | `onboarding/domain` or `onboarding/presentation`, depending on whether the rule is product or navigation state |
| `lib/use-device-monitoring.ts` | State coordination plus native/backend integration | A ViewModel/use case plus common contracts and platform implementations |
| `context/revenuecat-provider.tsx` | Application service wiring and lifetime | `app/di` plus a specifically named purchase capability if the app needs one |

The intended improvement is not “Kotlin requires more folders.” It is that code currently grouped under a broad RN name such as `lib` gets an owner based on what it means. `derivePersonStatus`, for example, is not a generic utility; it is monitoring domain logic. Aleph Bet's answer evaluation is similarly learning domain logic, not a helper and not UI code.

## Side-by-side comparison

| Concern | CMPMemeCreator | Aleph Bet | Why Aleph Bet differs |
|---|---|---|---|
| Gradle modules | One older combined `composeApp` module | One shared-code module plus thin `androidApp`; `iosApp` is the Xcode host | Current entry-point separation does not imply feature-module proliferation. |
| Top-level features and capabilities | `meme_editor`, `meme_gallery` | `home`, `onboarding`, `alphabet`, `learning`, `practice`, `progress`, `settings` | Our product has several durable responsibilities rather than one dominant editor. |
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

This is a destination map, not a command to create empty directories. A package appears when it has a real class or function to own.

## The top-level packages are not all the same kind

The map is **ownership-first**, not perfectly feature-only or perfectly layer-only. It intentionally contains three kinds of top-level package:

| Kind | Packages | Meaning |
|---|---|---|
| Application composition | `app` | Knows which destinations and dependencies make up the application. |
| Vertical product areas | `home`, `onboarding`, `alphabet`, `learning`, `practice`, `settings` | Own a user capability and whichever of domain, data, and presentation it actually needs. |
| Shared named capabilities | `design`, `audio`, `progress` | Serve several product areas but still have one narrow, explicit responsibility. |

“Shared” does not mean “put miscellaneous reusable code here.” A shared package must answer a stable question:

- `design`: how should reusable Aleph Bet UI look and behave?
- `audio`: how does common code request and observe playback?
- `progress`: what learning history exists and what does it mean?

This is why there is no generic `shared`, `common`, or `utils` package in the proposed map. Kotlin's `commonMain` already means cross-platform; it does not mean conceptually ownerless.

`home` and `onboarding` initially need only `presentation` because they coordinate and display concepts owned elsewhere. We should not invent `home/domain` merely to make its tree match Alphabet. If Home later gains a real product policy of its own, that policy can earn a domain package then.

## Is `alphabet` the alphabet grid screen?

No. The grid is one presentation owned by the broader Alphabet product area.

```text
alphabet/
  domain/
    Letter.kt
    LetterId.kt
    LetterForm.kt
    LetterSound.kt
    AlphabetRepository.kt

  data/
    AlphabetContentDto.kt
    BundledAlphabetRepository.kt
    AlphabetContentMapper.kt

  presentation/
    explorer/
      AlphabetScreen.kt
      AlphabetUiState.kt
      AlphabetViewModel.kt
    detail/
      LetterDetailScreen.kt
      LetterDetailUiState.kt
      LetterDetailViewModel.kt
```

The package owns two related things:

1. the app's canonical knowledge about Hebrew letters;
2. the learner-facing reference experience for browsing that knowledge.

It does **not** own lesson ordering, practice selection, or the meaning of `SOLID`. Those belong to Learning, Practice, and Progress respectively.

This is analogous to a Bearywell product area owning its pure status model and the screens that present that model, even if Expo Router requires its route entry files to live under the global `app/` directory.

## Will Practice use Alphabet?

Yes. Cross-package use is expected; the boundary controls **what** Practice may use.

```text
PracticeScreen
    -> PracticeViewModel
        -> BuildPracticePlan
            -> AlphabetRepository     letter facts and available choices
            -> ProgressRepository     what this learner needs to review
        -> LessonSessionEngine        run the resulting exercise queue
        -> AudioPlayer                play the selected prompt
```

Practice may depend on Alphabet's public domain concepts, such as `LetterId`, `Letter`, and `AlphabetRepository`. It must not depend on:

- `AlphabetScreen` or `AlphabetViewModel`;
- JSON DTOs or `BundledAlphabetRepository`;
- private UI state from the explorer;
- knowledge of how Koin constructs the repository.

Koin injects the `AlphabetRepository` implementation into the practice use case or ViewModel. Practice asks for letter facts through the contract; it neither reads `alphabet.json` nor constructs the repository itself.

In React Native terms, this is closer to importing a typed feature service or pure domain function than importing another route component. The fact that two screens show a glyph does not mean one screen reuses the other screen.

## How shared capabilities participate

The same practice flow uses several packages without making them one feature:

| Capability | What Practice uses | What remains hidden |
|---|---|---|
| `alphabet` | Letter IDs, glyph/name/sound facts, repository contract | JSON DTOs, content mapping, explorer UI |
| `learning` | Exercise and session types, queue execution, answer evaluation | Guided-lesson authoring details and lesson UI |
| `progress` | Progress snapshots and recording contracts | Room entities, DAOs, transactions, migrations |
| `audio` | Playback commands and observable playback state | AVFoundation and Android media-player types |
| `design` | Buttons, cards, typography, spacing | No product rules or learner state |

`progress` is shared in usage but it is not merely technical infrastructure. It is a business capability: it defines what `INTRODUCED`, `PRACTICING`, and `SOLID` mean. That is why it has a domain package as well as Room-backed data code.

`audio` is a technical capability. It knows how to play an asset and report playback state, but it does not know whether the audio represents alef, an exercise prompt, or a future word.

`design` is a UI capability. A generic `PrimaryButton` can live there; an `AlphabetProgressCard` stays in its owning feature even if it is visually card-shaped.

## One naming caveat: `learning`

In the current map, Learning owns guided lesson definitions and the session machinery reused by Practice. That creates a deliberate dependency from `practice/domain` to the public parts of `learning/domain`.

If implementation shows that the reusable session engine is a stable capability independent of authored lessons, we can extract a specifically named `session/domain` package:

```text
learning  -> creates an authored SessionPlan
practice  -> creates a review SessionPlan
session   -> runs either plan
```

We should make that extraction when the concrete classes reveal the shared API, rather than inventing a generic engine framework before the three-letter slice. Until then, `learning/domain` is the first owner and Practice may reuse its public session types.

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

This drawing is directional, not a ban on cross-feature dependencies and not a demand for a separate interface around every class. The important rules are:

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

Keep the feature packages inside one shared-code module through the three-letter vertical slice and the alphabet release. Consider extracting feature Gradle modules only when evidence appears:

- unrelated code repeatedly recompiles and profiling shows a useful module boundary;
- developers repeatedly violate an important dependency rule;
- a feature needs independent reuse or replacement;
- multiple owners need stable APIs and isolated builds;
- tests or platform variants become materially difficult to manage in one module.

The likely future module boundary is not automatically one module per current package. For example, a stable learning engine might eventually become a reusable module, while small presentation and settings packages could remain in the app module.

## React Native translation

| React Native idea | KMP meaning here |
|---|---|
| One app workspace/package | One shared-code Gradle module plus thin native application hosts |
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
