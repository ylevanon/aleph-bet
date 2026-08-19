# Lesson 00.02 — Repository, Gradle, packages, and source sets

Status: Ready

Module: 00 — Orientation and toolchain

Estimated focused time: 60–90 minutes, plus project download and first Gradle sync

## Product outcome

The repository contains a current Android-and-iOS Compose Multiplatform starter, and you can point to every generated part and explain whether it is a filesystem folder, Kotlin package, Gradle module, source set, target, or native host.

This lesson does **not** implement an Aleph Bet feature. Its product artifact is an annotated map of the build we will actually use.

## Learning outcomes

By the end, you can:

- distinguish a Gradle module from a Kotlin package and a KMP source set;
- trace shared Kotlin code into the Android and iOS applications;
- use `settings.gradle.kts` and each module's `build.gradle.kts` as evidence rather than guessing from folder names;
- place hypothetical common, Android-only, iOS-only, and Swift code correctly;
- explain why the generated structure, not an older tutorial's names, is authoritative for this project.

## Prerequisites

- Lesson 00.01 passed.
- A current Kotlin-Multiplatform-capable Android Studio or IntelliJ IDEA installation.
- Xcode installed and opened at least once.
- The existing Aleph Bet repository is available locally.

## Retrieval warm-up

Answer without notes:

1. Which long-lived facts belong in Room, and which preference named in 00.01 belongs in DataStore?
2. If the practice screen uses a queue builder, does that make the queue rule presentation logic? Why?
3. If an Android-only audio implementation and a shared `AudioPlayer` contract use the same package name, are they necessarily in the same source set?

## Why the app needs this now

The repository currently contains product documents, the authored alphabet asset pack, validation scripts, and Python tests. It does **not** yet contain a Gradle root, Kotlin source sets, an Android application, or an Xcode host.

That absence matters. A source-set lesson built around imaginary folders would teach names rather than structure. We will generate a compatible starter first, preserve the existing material, and then map the files that really exist.

## Mental model

These concepts answer different questions:

| Concept | Question it answers | Example |
|---|---|---|
| Filesystem folder | Where is the file stored? | `shared/src/commonMain/kotlin/` |
| Kotlin package | What namespace is the declaration in? | `com.alephbet.alphabet.domain` |
| Gradle module | What buildable/configurable unit owns it? | `:shared` or `:androidApp` |
| Source set | Which compilations share this code? | `commonMain`, `androidMain`, `iosMain` |
| Target | What platform output is Gradle compiling? | Android, iOS simulator ARM64 |
| Native host | What platform app starts and packages shared code? | Android `Activity`; iOS Xcode app |

A likely current generated shape is:

```text
repository
├── settings.gradle.kts
├── shared/                         Gradle module: shared KMP code
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/             shared production source set
│       ├── commonTest/             shared test source set
│       ├── androidMain/            Android implementation source set
│       └── iosMain/                shared iOS-family source set
├── androidApp/                     Android application Gradle module
└── iosApp/                         Xcode project and Swift entry point
```

The wizard may change names or create additional source sets. Do not rename them to make this diagram true. Record the generated structure, and use that map throughout the course.

## React Native bridge

| Familiar pattern | Kotlin/KMP concept | Where the analogy breaks |
|---|---|---|
| Workspace/package | Gradle module | A Gradle module defines compilations, targets, plugins, and dependency boundaries—not merely package-manager distribution. |
| TypeScript import path | Kotlin package/import | A package does not determine which platform can compile a file; its source set does. |
| `ios/` and `android/` native folders | `iosApp`, `androidApp`, platform source sets | KMP can also compile Kotlin itself for each target, so platform boundaries exist inside Kotlin modules too. |
| Metro/Babel configuration | Gradle settings and build scripts | Gradle constructs a task and dependency graph for multiple native outputs. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Gradle wrapper | Repository-pinned scripts and files that select the Gradle version. |
| Gradle module | A project included by the root settings and configured by a build script. |
| Source set | A named group of Kotlin sources and dependencies participating in one or more compilations. |
| `commonMain` | Production Kotlin that must compile for every target connected to it. |
| `commonTest` | Shared tests for common behavior. |
| `androidMain` | Kotlin compiled only for the Android target. |
| `iosMain` | A shared source set for supported iOS targets when the hierarchy provides it. |
| Package | A Kotlin namespace declared at the top of a `.kt` file. |
| Target | A platform family/configuration for which Kotlin produces output. |
| Entry point | Platform code that starts the application and hosts the shared UI. |

## Predict before generating

For each file, predict both its source set or host **and** its package. These are two separate answers.

1. A pure `LetterId` type.
2. An Android media-player implementation of a common audio contract.
3. The Swift `@main` application declaration.
4. A common test for alphabet ordering.

Then answer: could items 1 and 2 declare the same Kotlin package even though only one compiles for iOS?

## Guided lab

### Step 1 — Prove the starting state

At the repository root, inspect the tracked files and search for:

```text
settings.gradle.kts
build.gradle.kts
gradlew
shared/
composeApp/
androidApp/
iosApp/
```

Expected result before generation: none of the Gradle/KMP project files are present. Existing top-level product areas such as `docs/`, `asset-pack/`, `scripts/`, and `tests/` must remain intact.

Stop and explain why `asset-pack/data/alphabet.json` is neither a Kotlin package nor a KMP source set.

### Step 2 — Generate compatible boilerplate safely

Project-generation output is safe to copy; understanding its structure is the assignment.

1. Use the current Kotlin Multiplatform IDE wizard, or the official web wizard if the IDE plugin is unavailable.
2. Generate into a new empty temporary folder—not directly over this non-empty repository.
3. Select Android and iOS.
4. Select shared Compose UI for iOS.
5. Keep the wizard's **Include tests** option enabled so `commonTest` and target test tasks are generated.
6. Use the project ID/package root `com.alephbet` unless an existing signed bundle identifier requires a different value.
7. Do not add desktop, web, or server targets for V1.
8. Let the wizard choose a compatible Kotlin, Compose, Android Gradle Plugin, and Gradle combination. Do not copy versions from an older tutorial.

Before moving anything, show the generated top-level tree to the coach. Together we will identify the exact generated files that can be added while preserving `.git`, `README.md`, `docs/`, `asset-pack/`, `scripts/`, and `tests/`.

Expected result: a clean generated starter exists separately, and no Aleph Bet repository file has been overwritten.

### Step 3 — Read the build from the outside inward

After the scaffold is safely integrated, inspect in this order:

1. `settings.gradle.kts`: record every included Gradle module.
2. Root `build.gradle.kts` and version catalog, if generated: identify where plugin and library versions come from.
3. The shared module's `build.gradle.kts`: identify targets and the `sourceSets` block.
4. The Android application module: find its dependency on shared code and its application identifier.
5. `iosApp`: find the Swift entry point and the configuration containing the bundle identifier.

Do not try to understand every plugin declaration yet. For each file, write one sentence beginning with “This file owns…”.

### Step 4 — Create the authoritative project map

Fill this table using exact generated names:

| Role | Actual path/name | Evidence |
|---|---|---|
| Gradle root |  |  |
| Shared Kotlin module |  |  |
| Android application module |  |  |
| iOS Xcode host |  |  |
| Common production source set |  |  |
| Common test source set |  |  |
| Android-specific Kotlin source set |  |  |
| iOS-family Kotlin source set(s) |  |  |
| Shared UI root Composable |  |  |
| Android entry point |  |  |
| iOS entry point |  |  |
| Kotlin package root |  |  |

“The folder looked right” is not sufficient evidence. Cite a settings entry, build-script declaration, package declaration, or entry-point call.

### Step 5 — Reconcile the architecture docs

Compare the generated map with [Project structure and platform boundaries](../../architecture/project-structure.md).

The architecture's responsibilities should survive even if names differ. If the current starter uses `shared` plus `androidApp`, update future course vocabulary to those names. Do not restructure working generated code merely to resemble CMPMemeCreator or an older `composeApp` layout.

## Independent task

Place each hypothetical declaration and justify all three dimensions: Gradle module/host, source set, and Kotlin package.

1. `Letter` domain model.
2. `AlphabetRepository` contract.
3. Android audio implementation.
4. iOS audio implementation written in Kotlin.
5. Swift app-signing configuration.
6. Shared Compose `LetterCard`.
7. Room database-path creation for each platform.
8. A test of a pure practice-queue rule.

### Acceptance criteria

- Every answer names the owner, platform reach, and package purpose.
- Common domain code has no Android, iOS, Swift, or Compose dependency.
- Platform implementations may share a package name with their common contract without being confused with `commonMain`.
- `iosApp` is recognized as a native host, not a second copy of the shared feature architecture.
- The answer does not create a generic `common` or `utils` dumping ground.

## Test and debugging plan

At this stage, validation is structural:

1. Gradle sync completes or produces a recorded first relevant error.
2. Every module in the map appears in `settings.gradle.kts` or is correctly identified as the Xcode host.
3. Every source set in the map appears in the shared build configuration or generated source hierarchy.
4. Package declarations match their directory intent, while acknowledging Kotlin does not require folders and packages to match.
5. Existing docs and asset files remain present.

Before changing anything after a failed sync, record:

```text
Observed failure:
First relevant error:
My hypothesis:
Smallest check that would disprove it:
```

## Hint ladder

<details>
<summary>Hint 1 — Placement</summary>

First ask whether the declaration needs a platform-only API. If not, start from the common source set and then choose the product package.

</details>

<details>
<summary>Hint 2 — Module versus source set</summary>

The shared Gradle module contains several source sets. `commonMain` is not normally a separate Gradle module.

</details>

<details>
<summary>Hint 3 — iOS boundary</summary>

Kotlin implementations for Apple targets belong in an iOS source set. Swift entry points, signing, and Xcode configuration belong in `iosApp`.

</details>

## Teach-back

Using one concrete file, explain why its filesystem path, package, Gradle module, and source set are four related but non-identical facts. Then trace how the shared `App` Composable reaches both platform entry points.

## Exit ticket

1. What does `commonMain` guarantee that a package named `common` cannot?
2. Why can `androidMain` depend on Android APIs while `commonMain` cannot?
3. Why does a shared-UI KMP app still need Android and iOS entry points?
4. Where do you verify that a Gradle module exists?
5. What evidence would justify adding separate feature Gradle modules later?

## Review rubric

This lesson assesses KMP source-set boundaries, architecture ownership, debugging discipline, and explanation quality. Passing requires an evidence-backed map of the generated project, not memorization of the sample diagram.

## Completion evidence

- [ ] Current KMP starter is integrated without overwriting existing repository material.
- [ ] Exact generated modules, source sets, packages, and entry points are mapped.
- [ ] Independent placement task is correct.
- [ ] Initial sync result is recorded.
- [ ] Learner explains the shared-to-native path.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 00.03 uses the map to run each target and diagnose a deliberate compiler failure. Module 03 will revisit source-set dependency rules after real resources and platform services exist.

## References

- [Kotlin Multiplatform quickstart](https://kotlinlang.org/docs/multiplatform/quickstart.html)
- [Create a Compose Multiplatform app](https://kotlinlang.org/docs/multiplatform/compose-multiplatform-getting-started.html)
- [Recommended KMP project structure](https://kotlinlang.org/docs/multiplatform/multiplatform-project-recommended-structure.html)
- [Kotlin Multiplatform Gradle DSL reference](https://kotlinlang.org/docs/multiplatform/multiplatform-dsl-reference.html)
