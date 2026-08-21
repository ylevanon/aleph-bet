# Bearywell to Aleph Bet: React Native and KMP architecture

Status: Learning and architecture guide

Last updated: 2026-08-19

## Purpose

This guide translates the structure and development instincts used in Bearywell's Expo/React Native app into the Kotlin Multiplatform and Compose Multiplatform approach proposed for Aleph Bet.

It separates:

- **functional differences**: how code renders, holds state, performs asynchronous work, survives lifecycle changes, and reaches platform APIs;
- **architectural differences**: how we choose to divide screens, product rules, persistence, and application assembly.

The distinction matters because KMP does not require Clean Architecture, repositories, use cases, or one ViewModel per screen. Those are design choices we apply only where Aleph Bet's behavior benefits from them.

## Executive summary

Bearywell is route-first and integration-heavy. Expo Router establishes the screen tree, route components often subscribe directly to Convex data, shared React components live in `components`, and a broad `lib` directory holds pure product rules, hooks, adapters, and platform integrations.

Aleph Bet is local-state and learning-rule-heavy. Compose navigation establishes destinations, ViewModels expose screen state, plain Kotlin owns session transitions, bundled resources own curriculum, and Room owns learner history. Packages make the owner of each rule explicit.

Neither approach is universally better. The product pressures are different:

| Bearywell pressure | Aleph Bet pressure |
|---|---|
| Authenticated, server-backed care-circle data | Offline authored curriculum |
| Live Convex queries and mutations | Local Room flows and transactions |
| Expo-managed native integrations and background monitoring | Shared learning behavior plus small audio/database platform seams |
| Many file-based destinations and onboarding branches | A small destination graph with behavior-rich lesson states |
| Business rules split between backend and native client | Learning rules primarily on-device and shared across Android/iOS |

## One request flowing through each app

### Bearywell-style flow

A route component can subscribe to server state and render it directly:

```text
Expo route component
    -> Convex useQuery / useMutation
    -> React render
    -> shared component
```

Pure calculations may be imported from `lib`, while hooks coordinate native or backend behavior.

### Aleph Bet practice flow

The equivalent learning flow has more explicit local owners:

```text
PracticeRoute
    -> obtains PracticeViewModel from Koin
    -> PracticeScreen(UiState, onAction)

PracticeViewModel
    -> BuildPracticePlan
         -> AlphabetRepository
         -> ProgressRepository
    -> LessonSessionEngine
    -> AudioPlayer
    -> StateFlow<PracticeUiState>

ProgressRepository implementation
    -> Room

AlphabetRepository implementation
    -> bundled JSON and resources
```

The extra names exist because Practice combines immutable curriculum, durable local progress, transient session state, and platform audio. They are not ceremony added merely because the language is Kotlin.

## Rendering: React components and Composables

### React Native

A React function component runs to describe UI. Hooks associate state and effects with its position in the React tree. Props carry data downward, callbacks carry intent upward, and state changes schedule another render.

### Compose Multiplatform

A function annotated `@Composable` participates in a compiler-supported composition. State reads are tracked so Compose can recompose affected UI when that state changes.

The familiar shape remains:

```text
state down -> UI -> events up
```

But the APIs are not lifecycle-identical:

| React Native | Compose | Important distinction |
|---|---|---|
| Function component | `@Composable` function | A Composable is transformed by the Compose compiler; it is not a function returning a virtual-DOM node. |
| Props | Function parameters | Prefer immutable values and callbacks in both. |
| `useState` | `remember { mutableStateOf(...) }` | Both are local UI state; `remember` survives recomposition, not process death. |
| Persist small UI state | `rememberSaveable` | Saveability is explicit and limited to supported or custom-saveable values. |
| `useEffect` | `LaunchedEffect` / `DisposableEffect` | Choose by effect lifecycle; do not translate effects mechanically. |
| Memoized derived value | `remember(keys) { ... }` / `derivedStateOf` | Use only when calculation or invalidation warrants it. |
| Context | Composition local or injected dependency | Composition locals suit UI-tree concerns such as theming, not arbitrary service location. |

In both frameworks, recomposition or re-render must be safe. Rendering code should describe UI, not perform uncontrolled persistence, navigation, or audio side effects.

## Routes and screens

### Bearywell

Expo Router makes the filesystem part of the navigation definition:

```text
app/(tabs)/index.tsx       -> route
app/person/[circleId].tsx -> parameterized route
app/_layout.tsx           -> navigation layout and providers
```

A default-exported file under `app` is a destination. The route path and directory tree are coupled intentionally.

### Aleph Bet

Compose navigation uses an explicit graph and typed route declarations. A Kotlin package or screen filename does not automatically become a destination:

```text
app/navigation/
  AppNavigation.kt
  Route.kt

alphabet/presentation/explorer/
  AlphabetRoute.kt
  AlphabetScreen.kt
```

The split between `Route` and `Screen` is useful:

- the route-level Composable reads route arguments, obtains the ViewModel, collects state, and performs navigation;
- the screen Composable receives plain state and callbacks, making previews and UI tests easy.

Not every visual change is a destination. Letter introduction, question, feedback, and recap are states inside one lesson-session route. This is the KMP equivalent of resisting the urge to create an Expo route for every conditional panel in a single workflow.

## Local state, screen state, and durable state

Bearywell often colocates local state in a route with `useState`, receives live server state through hooks, and keeps durable truth in Convex. Aleph Bet is offline-first, so the state boundaries differ:

| Lifetime | Aleph Bet owner | Example |
|---|---|---|
| One visual interaction | Compose `remember` | Button pressed state, animation progress |
| Re-creatable visual state | `rememberSaveable` where appropriate | A simple expanded/collapsed flag |
| Screen or active session | ViewModel and `StateFlow` | Current exercise, choices, feedback |
| Process-safe checkpoint | Room or saved-state mechanism | Next meaningful guided-lesson step |
| Durable learner history | Room | Attempts, completed lessons, last practiced time |
| Small preference | DataStore | Audio preference, completed onboarding |
| Authored product content | Bundled resources | Letter facts, explanations, lesson definitions |

The ViewModel is closer to a screen-scoped Zustand store than to a React component. It survives ordinary Android configuration changes and exposes immutable state, but it is not a database and should not be treated as guaranteed process-death persistence.

## Hooks versus ViewModels, use cases, and services

A React hook can combine several roles:

- subscribe to external state;
- own local React state;
- run lifecycle effects;
- expose commands;
- call native modules;
- call a backend.

KMP usually separates those roles because their lifetimes and platform availability differ:

| Hook responsibility | Aleph Bet owner |
|---|---|
| Produce state for one route | ViewModel |
| Apply a pure product rule | Domain function, engine, or use case |
| Observe durable local data | Repository returning `Flow` |
| Execute scoped async work | Coroutine in an appropriate scope |
| Reach Android/iOS behavior | Common interface plus platform implementation |
| Assemble dependencies | Koin application graph |

This does not mean every Bearywell hook becomes five Kotlin classes. A tiny calculation remains a function. We split only when behavior has a different lifetime, platform boundary, or reason to change.

## Async work: promises and coroutines

### React Native

Bearywell uses promises, `async` functions, React hooks, Convex hooks, and background integrations. Cancellation and component lifetime depend on the library and effect cleanup.

### Kotlin

Aleph Bet uses:

- `suspend` functions for one asynchronous result;
- coroutines for cancellable work;
- `Flow<T>` for a stream of values;
- `StateFlow<T>` for observable current state;
- lifecycle-aware scopes such as `viewModelScope` for screen coordination.

The architectural benefit is structured concurrency: work belongs to a scope with an explicit lifetime. The danger is launching work in an application-wide scope merely to avoid thinking about ownership.

Useful translation:

| TypeScript/React | Kotlin |
|---|---|
| `Promise<T>` | `suspend fun ...: T` |
| Subscription/callback stream | `Flow<T>` |
| Screen store snapshot | `StateFlow<UiState>` |
| `try/catch` around `await` | `try/catch` around a suspend call |
| Effect cleanup | Coroutine cancellation or `DisposableEffect`, depending on the owner |

## Dependency access

### Bearywell

Dependencies are commonly reached through:

- direct ES imports;
- React context providers;
- hooks supplied by libraries;
- generated Convex API imports;
- module-level singleton clients.

The root layout establishes providers such as authentication and purchases.

### Aleph Bet

Koin builds the object graph, mostly through constructor injection:

```text
PracticeViewModel
    needs BuildPracticePlan
    needs LessonSessionEngine
    needs ProgressRepository
    needs AudioPlayer
```

Koin Annotations describe how production objects are assembled. Domain and ViewModel tests normally call constructors directly with fakes.

DI is not a replacement for imports. `PracticeViewModel` still imports the types it explicitly depends on. Koin supplies runtime instances of those types. Plain immutable values and pure functions do not need to be injected automatically.

## Server state and offline local state

The largest data-layer difference comes from the products, not the UI frameworks.

### Bearywell

Convex is authoritative for shared user and care-circle information. Route components can observe live queries, invoke mutations, and let the backend enforce shared rules. Local native integrations collect signals and synchronize relevant information.

### Aleph Bet V1

There is no account or backend:

```text
Bundled JSON/resources  -> what letters and lessons exist
Room                    -> what this learner has done
DataStore               -> small preferences
ViewModel/session       -> what is happening right now
```

Repositories are useful because screens and domain rules should ask for alphabet or progress information without knowing whether it came from JSON, Room, or a test fake. They are not speculative preparation for a future backend.

## Platform-specific code

### Expo/React Native

Bearywell is Expo-managed. Most application code runs through React Native, while config plugins and focused native modules provide behavior that JavaScript cannot supply. Platform-specific files and runtime platform checks can select different implementations.

### KMP

Platform availability is expressed at compile time through source sets:

```text
commonMain   shared Compose, domain, ViewModels, repository contracts
androidMain  Android entry point and platform implementations
iosMain      iOS bridge and platform implementations
desktopMain  JVM development host and substitutes for Hot Reload
iosApp       Xcode host, signing, bundle configuration
```

Code in `commonMain` cannot casually import an Android SDK or iOS framework type. A common `AudioPlayer` contract can be implemented with the appropriate APIs in each platform source set. The desktop target exists to accelerate shared development; Android and iOS remain the V1 release targets.

Prefer an ordinary common interface with injected implementations. Use `expect`/`actual` when the declaration itself truly needs target-specific compilation. We do not need one `expect`/`actual` pair per platform difference.

## Styling, assets, and shared components

| Bearywell | Aleph Bet |
|---|---|
| Unistyles theme | Compose `MaterialTheme` plus Aleph Bet tokens |
| `StyleSheet.create` | Modifiers, theme values, and reusable Composables |
| `assets/images` | Compose Multiplatform resources |
| `components/button.tsx` | `design/components/PrimaryButton.kt` if genuinely reusable |
| Feature-sized component in global `components` | Owning feature's `presentation/components` package |
| Image or font module import | Generated Compose resource accessor or resource-loading API |

Compose's `Modifier` is not a style object. It is an ordered chain that can affect layout, drawing, input, semantics, and behavior. Modifier order can change the result, so React Native style intuition does not transfer perfectly.

## Testing translation

| Bearywell test target | Aleph Bet test target |
|---|---|
| Pure function in `lib` | Plain Kotlin domain test in `commonTest` |
| React component | Compose UI test |
| Hook with mocked modules | ViewModel test with fake constructor dependencies |
| Convex function | Repository or backend test; Aleph Bet V1 has no backend |
| Native integration | Android/iOS integration test |
| Navigation screen | Route/navigation test plus stateless screen test where useful |

Aleph Bet's highest-value tests target the session engine, answer evaluation, progress transitions, content validation, and Room migrations. Snapshotting every Composable would provide less confidence than proving those learning rules.

## Package translation from Bearywell

This is a conceptual mapping, not a mechanical migration recipe:

| Bearywell location | Likely KMP destination | Placement question |
|---|---|---|
| `app/` | Feature `presentation` plus `app/navigation` | Is this a destination, screen implementation, or global graph? |
| `components/` | `design/components` or feature `presentation/components` | Is it generic visual language or tied to one product concept? |
| `lib/` pure rule | Owning feature's `domain` | Which product decision does it encode? |
| `lib/` query/storage adapter | Owning feature's `data` | Which source of truth does it translate? |
| `lib/` native integration | Common capability plus platform source-set implementation | Which API can common code safely see? |
| `context/` | `app/di`, ViewModel state, or a narrow CompositionLocal | Is this object graph wiring, screen state, or UI-tree context? |
| `utils/` | Owner-specific function; rarely a top-level package | Why is it reusable, and who owns its meaning? |
| Expo config plugin | Android/iOS build or platform integration | Is this compile-time setup or runtime behavior? |

## What is required and what is our choice

### KMP or platform constraints

- common code may use only APIs available to its target set;
- Android and iOS builds compile against different platform APIs;
- the iOS app still needs an Xcode host;
- source-set dependencies determine which libraries code can import;
- lifecycle and process behavior differ across platforms and must be tested.

### Aleph Bet architecture choices

- shared Compose UI;
- one `shared` KMP code/UI module plus thin Android and iOS application hosts initially;
- ownership-first Kotlin packages;
- ViewModels for behavior-rich destinations;
- plain Kotlin session and progress rules;
- Koin Annotations;
- Room for learner progress;
- DataStore for preferences;
- bundled JSON/resources for curriculum.

We can change an architecture choice when the vertical slice disproves it. We cannot ignore a source-set constraint simply because a React Native import previously worked on both devices.

## Translation mistakes to avoid

### Turning every Composable into a component with a ViewModel

Most reusable Composables should receive values and callbacks. ViewModels belong at screen or session boundaries, not inside buttons, cards, or individual letter cells.

### Treating `remember` as durable storage

It is closer to local render-tree state. Room, DataStore, or a deliberate saved-state mechanism owns data that must survive longer.

### Recreating `lib` as `core` or `utils`

Put answer evaluation under Learning, letter facts under Alphabet, and learning history under Progress. Sharing does not erase ownership.

### Creating a use case for every repository call

A use case earns its name when it applies or coordinates product behavior. A ViewModel may call a repository directly for a simple read.

### Injecting every value through Koin

Inject stable services and collaborators. Pass screen arguments, IDs, and ordinary values explicitly.

### Using `expect`/`actual` everywhere

An interface in `commonMain` with Android and iOS implementations is often easier to inject and fake. Reserve `expect`/`actual` for declarations that genuinely need it.

### Assuming packages enforce dependencies

Inside one Gradle module, packages communicate intent but do not prevent an import. If recurring violations appear, that may become evidence for module extraction later.

## Recommended learning sequence

Use the three-letter slice to translate one concept at a time:

1. Build a stateless letter Composable: props and callbacks become parameters and lambdas.
2. Add local state: compare `remember` with `useState`.
3. Load bundled letters: understand `commonMain` resources and DTO-to-domain mapping.
4. Write a pure session transition: compare it with a reducer or pure `lib` rule.
5. Add a ViewModel and `StateFlow`: compare it with a screen-level store or coordinating hook.
6. Add Koin assembly: compare constructor injection with provider/context wiring.
7. Add Room progress: make state lifetime explicit.
8. Add audio implementations: exercise `commonMain`, `androidMain`, and `iosMain` boundaries.
9. Test process interruption, not only recomposition.

At each step, ask two questions:

1. Is this difference required by Kotlin/KMP, or is it our architecture choice?
2. Which object owns this state or rule after the screen disappears?

## Primary references

- [Expo Router core concepts](https://docs.expo.dev/router/basics/core-concepts/)
- [Kotlin Multiplatform source sets and platform implementations](https://kotlinlang.org/docs/multiplatform/multiplatform-create-first-app.html)
- [Kotlin Multiplatform Gradle and source-set reference](https://kotlinlang.org/docs/multiplatform/multiplatform-dsl-reference.html)
- [Android UI architecture and unidirectional data flow](https://developer.android.com/topic/architecture/ui-layer)
- [Compose UI architecture](https://developer.android.com/develop/ui/compose/architecture)
