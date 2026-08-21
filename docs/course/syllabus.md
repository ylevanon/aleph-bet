# Complete course syllabus

Status: Proposed sequence; adapts to learner evidence

Last updated: 2026-08-19

## How to read the syllabus

Each lesson produces two outcomes:

- a **learning outcome** the learner can demonstrate;
- a **product artifact** that advances Aleph Bet V1.

Lesson pages are expanded just before their module begins. Writing all 59 full solutions now would make the course generic, reveal assignments prematurely, and lock decisions before the vertical slice provides evidence. This syllabus fixes the learning sequence and acceptance gates while allowing individual lessons to adapt.

Testing is integrated throughout rather than saved for a final testing module.

## Module 00 — Orientation and toolchain

Product gate: the project runs on Android and iOS, shared UI has a fast desktop Hot Reload loop, and the learner's baseline is recorded.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 00.01 | Baseline and course map | Explain current strengths, unknowns, and the product-to-code roadmap | Diagnostic record and learning contract |
| 00.02 | Repository, Gradle, modules, packages, and source sets | Distinguish filesystem folders, Kotlin packages, source sets, Gradle modules, and the Xcode host | Annotated project map |
| 00.03 | The fast shared-UI development loop | Trace both native hosts, distinguish previews from real targets, and use Desktop Hot Reload | Shared starter verified on Android/iOS plus a desktop development host |

Checkpoint questions:

- What does `commonMain` mean that a Kotlin package does not?
- Why is `iosApp` present if UI is shared?
- Where would an Android-only audio class compile?

## Module 01 — Kotlin through the alphabet domain

Product gate: three letter forms are represented by valid domain models with common tests.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 01.01 | Values, variables, types, and expressions | Use scoped declarations, inference, interpolation, and value-producing `if` expressions | Visible alphabet progress and state-derived button labels |
| 01.02 | Functions, named arguments, and null safety | Extract typed transformations and safely format optional content | Pure progress formatter and nullable Alef/Bet labels |
| 01.03 | Data classes, value classes, enums, and sealed types | Choose a Kotlin type based on valid states rather than habit | `LetterId`, `LetterForm`, and sound variants |
| 01.04 | Collections and higher-order functions | Use `map`, `filter`, `associate`, grouping, and lambdas idiomatically | Alphabet lookup and final-form transformations |
| 01.05 | Interfaces, mappings, and dependency direction | Separate a domain contract from source-shaped content | Repository contract and DTO-to-domain mapper design |
| 01.06 | `kotlin.test` and the Kotlin checkpoint | Write focused tests and explain compiler feedback | Passing domain validation tests and independent fourth-letter task |

Checkpoint challenge: add a new letter form, one sound variant, and tests without copying an existing declaration line for line.

## Module 02 — Compose foundations

Product gate: an accessible, hard-coded three-letter explorer renders predictably with stateless components and tested interactions.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 02.01 | Composables and recomposition | Explain composition without treating it as React's virtual DOM | First shared letter card |
| 02.02 | Layout, `Modifier`, theme, and modifier order | Build responsive layout and predict modifier effects | Aleph Bet theme and styled card |
| 02.03 | Parameters, callbacks, components, and previews | Hoist state and distinguish reusable design from feature UI | Stateless `LetterCard` plus previews |
| 02.04 | `remember`, saveable state, and effects | Assign local state and effects to the correct lifecycle | Audio-button placeholder and selection interaction |
| 02.05 | Lazy grids, keys, RTL, and semantics | Render collections with stable identity and accessible Hebrew UI | Three-letter alphabet grid |
| 02.06 | Compose UI checkpoint | Test semantics and implement an independent UI variation | UI tests and hard-coded explorer checkpoint |

Checkpoint challenge: add a fourth letter and a selected-state treatment without adding a ViewModel or duplicating the card.

## Module 03 — KMP boundaries and resources

Product gate: real shared font and three-letter content load on both targets through valid source-set dependencies.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 03.01 | Targets, source sets, and dependencies | Predict which imports compile in common and platform code | Dependency and source-set map |
| 03.02 | Compose resources, fonts, JSON, and audio assets | Package and resolve shared resources safely | Noto Sans Hebrew and three-letter resource subset |
| 03.03 | Common interfaces versus `expect`/`actual` | Choose the smallest platform boundary and make it fakeable | First platform-capability exercise |
| 03.04 | Cross-platform checkpoint | Diagnose a deliberate source-set mistake and verify resource parity | Same three letters rendered on Android and iOS |

Checkpoint challenge: explain why a file belongs in its source set before moving it.

## Module 04 — Alphabet feature vertical slice

Product gate: authored JSON flows through explicit models to an explorer and stable letter-detail destination.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 04.01 | Content schema, DTOs, and domain invariants | Separate the wire/content shape from a valid domain object | Three-letter content schema and DTOs |
| 04.02 | Serialization, validation, and mapping | Fail clearly when bundled content is inconsistent | Resource loader, mapper, and validation tests |
| 04.03 | Repository contracts, fakes, and implementations | Explain what a repository hides and what it should not hide | `AlphabetRepository`, fake, and bundled implementation |
| 04.04 | Connect content to presentation | Load asynchronous content without leaking DTOs into UI | Explorer driven by real bundled data |
| 04.05 | Typed letter detail and feature checkpoint | Pass stable IDs through navigation and derive screen state | Explorer-to-detail flow on both targets |

Checkpoint challenge: corrupt one content reference, predict the failure layer, then restore it and add a fourth letter only through content.

## Module 05 — Learning session engine with TDD

Product gate: a plain Kotlin engine runs one authored three-letter lesson deterministically and has no Compose, Room, Koin, Android, or iOS dependency.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 05.01 | Behavior examples and session vocabulary | Turn the product lesson loop into examples before class design | Session scenarios and glossary |
| 05.02 | Sealed steps and valid session states | Model introduction, exercise, feedback, and recap without impossible combinations | Lesson/session domain model |
| 05.03 | State transitions as pure functions | Test state plus action producing a new state | First transition tests and engine |
| 05.04 | Deterministic choices and controlled randomness | Inject variability without flaky tests or changing answers during recomposition | Stable multiple-choice generation |
| 05.05 | Authored lesson queues and validation | Convert curriculum definitions into a valid session plan | Three-letter lesson definition and queue builder |
| 05.06 | Incorrect answers, completion, and engine checkpoint | Define edge behavior and extend it independently | Complete tested lesson engine |

Checkpoint challenge: implement one new exercise transition and defend whether it belongs in the engine, ViewModel, or UI.

## Module 06 — Presentation state, navigation, and Koin

Product gate: the lesson engine is driven through immutable UI state and actions, with dependencies assembled by Koin Annotations.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 06.01 | Coroutines, `suspend`, `Flow`, and `StateFlow` | Distinguish one result, a stream, and current observable state | Small flow lab using alphabet data |
| 06.02 | ViewModel, `UiState`, and `UiAction` | Coordinate a session without moving engine rules into the ViewModel | `LessonViewModel` and state mapping |
| 06.03 | Route versus stateless screen | Collect lifecycle-aware state and keep UI previewable | Lesson route and lesson screen |
| 06.04 | Typed navigation and session boundaries | Navigate between destinations without routing each session card | Home, lesson, explorer, and detail graph |
| 06.05 | Koin Annotations and presentation checkpoint | Explain constructor injection, scopes, generated bindings, and direct test construction | Annotated graph and ViewModel tests with fakes |

Checkpoint challenge: replace a dependency with a fake in a test without starting Koin.

## Module 07 — Room and durable progress

Product gate: meaningful lesson and practice facts survive app restart, and the schema can evolve without discarding learner data.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 07.01 | From behavior to relational facts | Decide what must be durable before designing tables | Schema decision record |
| 07.02 | Room KMP setup and database construction | Separate common declarations from platform database paths/builders | Room database builds on Android and iOS |
| 07.03 | Entities, keys, DAOs, `suspend`, and `Flow` | Model relational storage and test queries | Progress entities, DAOs, and DAO tests |
| 07.04 | Entity mappings, repositories, and transactions | Keep persistence shapes out of domain/UI and update facts atomically | Room-backed `ProgressRepository` |
| 07.05 | Attempts and learning-stage transitions | Store evidence separately from interpretations and test the rules | Attempt recording and progress policy |
| 07.06 | Resume, process death, migrations, and Room checkpoint | Restore the next safe lesson step and protect data across versions | Resume behavior, exported schema, and migration test |

Checkpoint challenge: explain every persisted column by naming a product behavior that reads or writes it.

## Module 08 — Audio and platform integration

Product gate: reviewed letter-name and sound audio works through one common contract on real Android and iOS devices.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 08.01 | Playback contract, resource identity, and lifecycle | Define what common learning code needs without exposing native types | `AudioPlayer` contract and fake |
| 08.02 | Android implementation | Implement and debug the contract with Android lifecycle behavior | Android playback implementation |
| 08.03 | iOS implementation | Implement the same behavior using iOS APIs and interruption rules | iOS playback implementation |
| 08.04 | Audio UI, interruptions, and checkpoint | Coordinate playback state, rapid taps, exit, and accessibility | Audio-enabled lesson and detail screens |

Checkpoint challenge: compare an Android/iOS behavior difference and decide whether the contract or implementation should change.

## Module 09 — Practice and full application integration

Product gate: the complete three-letter vertical slice teaches, remembers, reviews, resumes, and reports progress offline.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 09.01 | Practice policy from progress evidence | Convert product priorities into a testable selection rule | Review-policy examples and tests |
| 09.02 | Practice queue construction | Reuse session machinery without creating a second engine | Deterministic practice-plan builder |
| 09.03 | Practice presentation | Coordinate plan, engine, attempts, progress, and audio | Practice ViewModel and screen |
| 09.04 | Home aggregation and resume | Combine flows into defensible summary UI | Home state, continue action, and progress summary |
| 09.05 | Settings, DataStore, reset, and vertical-slice defense | Separate preferences from relational history and verify the end-to-end architecture | Complete three-letter slice and review report |

Checkpoint challenge: trace one wrong answer from tap through persisted evidence, changed practice priority, and recomposed UI.

## Module 10 — Complete alphabet and product quality

Product gate: all 22 letters and five final forms meet the V1 learning promise across supported devices and accessibility settings.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 10.01 | Curriculum sequence and content review | Separate editorial decisions from engine behavior | Final authored lesson sequence |
| 10.02 | Scaling by content, not architecture | Prove that adding forms does not require feature rewrites | Full alphabet and final forms loaded |
| 10.03 | Confusable review and progress calibration | Test review behavior without claiming educational mastery | Confusion-aware review and calibrated stages |
| 10.04 | Accessibility, RTL, large text, and semantics | Test Hebrew UI beyond the default simulator configuration | Accessibility and RTL remediation |
| 10.05 | Offline, performance, lifecycle, and quality gate | Profile and validate realistic interruptions and devices | Complete-alphabet QA report |

Checkpoint challenge: demonstrate that the app remains useful offline and that a fourth lesson is authored rather than hard-coded into presentation.

## Module 11 — Release and architecture defense

Product gate: a store-ready release candidate exists and the learner can defend, critique, and extend its architecture.

| ID | Lesson | Learning outcome | Product artifact |
|---|---|---|---|
| 11.01 | Privacy, attribution, versioning, and release configuration | Identify store obligations for an offline learning app | Release settings and compliance checklist |
| 11.02 | App icon, screenshots, metadata, and device matrix | Present the product accurately and test release surfaces | Store asset and metadata package |
| 11.03 | Signed builds and internal testing | Produce, distribute, and evaluate release candidates | Android internal build and iOS TestFlight candidate |
| 11.04 | Final architecture defense and retrospective | Explain tradeoffs, diagnose a change request, and design V1.1 without tutorial support | Final defense, gap assessment, and next roadmap |

Final challenge: receive a new requirement, propose its screen/state/domain/data impact, and implement a bounded portion without a prepared walkthrough.

## Intentionally excluded from this course

- backend, accounts, and cloud sync;
- vocabulary and niqqud curriculum;
- purchase implementation;
- automated pronunciation analysis;
- generic enterprise architecture;
- multiple feature Gradle modules without measured need;
- Android-only or iOS-only UI rewrites.

These may become later courses or app iterations. Their absence keeps the first course deep and shippable rather than broad and unfinished.

## Technical references by phase

- Kotlin language: [official Kotlin tour](https://kotlinlang.org/docs/kotlin-tour-welcome.html)
- KMP structure: [official KMP documentation](https://kotlinlang.org/docs/multiplatform.html)
- Coroutines: [official coroutines basics](https://kotlinlang.org/docs/coroutines-basics.html)
- Room KMP: [Android Developers Room KMP guide](https://developer.android.com/kotlin/multiplatform/room)
- Koin Annotations: [Koin compiler-plugin annotation guide](https://insert-koin.io/docs/reference/koin-annotations/start/)

Lesson pages will link the narrow official reference needed for that lesson rather than assigning large documentation sections without a concrete task.
