# Assessment and progress

Status: Lesson 01.03 passed; Lesson 01.04 ready

Last updated: 2026-08-19

## Why the course tracks evidence

Finishing a lesson page does not prove that a skill transferred. Progress is based on observable evidence: prediction, implementation, tests, debugging, explanation, and later recall.

The purpose is not school-style grading. It is to answer honestly:

- What can the learner recognize?
- What can the learner explain?
- What can the learner build with guidance?
- What can the learner build independently?
- What can the learner debug and teach?

## Lesson statuses

| Status | Meaning |
|---|---|
| Not started | No diagnostic or implementation attempt yet |
| In progress | Lesson is active |
| Submitted | Learner has presented the required evidence for review |
| Needs revision | Specific gaps remain; feedback is recorded |
| Passed | Acceptance criteria and explanation standard are met |
| Revisit | Previously passed concept showed a later retrieval gap |

Only one lesson should normally be `In progress`.

## Mastery scale

Each skill can have different levels at the same time.

| Level | Name | Evidence |
|---:|---|---|
| 0 | Unfamiliar | Cannot yet identify the concept |
| 1 | Recognize | Understands an example while it is visible |
| 2 | Explain | Describes the purpose and compares it with React Native |
| 3 | Guided implementation | Builds correctly with prompts or a partial skeleton |
| 4 | Independent implementation | Designs and implements a bounded task from acceptance criteria |
| 5 | Debug and teach | Diagnoses failures, explains tradeoffs, and transfers the concept to a new case |

Course completion does not require level 5 in every library API. It does require level 4 or 5 in the core skills that define the app.

## Core skill matrix

Baseline levels are not assigned from language trivia or self-reported framework fluency during lesson 00.01. Each value is recorded when the first authentic Kotlin task produces implementation, debugging, or explanation evidence. Target levels describe the expected evidence by course completion.

| Skill | Baseline | Target | Evidence source |
|---|---:|---:|---|
| Kotlin syntax and null safety | 3 | 4 | Visible value/expression implementation and later domain variation |
| Kotlin type modeling | 3 | 5 | Letter/LetterId implementation, state-family reasoning, and later architecture defense |
| Collections and functional transformations | TBD | 4 | Alphabet mapping and queue building |
| Compose mental model | 2 | 4 | UI implementation and recomposition diagnosis |
| Compose state and effects | 2 | 5 | Session UI and lifecycle debugging |
| KMP source sets and boundaries | 3 | 5 | Platform placement challenge, desktop target explanation, and audio integration |
| Coroutines and Flow | TBD | 4 | ViewModel and Room flow integration |
| ViewModels and UDF | 2 | 5 | Lesson/practice presentation and teach-back |
| Architecture and package ownership | 2 | 5 | Placement exercises and final defense |
| Testing | TBD | 5 | Domain TDD, database, UI, and migration tests |
| Room and relational modeling | 2 | 4 | Schema, transactions, resume, and migration |
| Koin and constructor injection | TBD | 4 | Production graph and direct-construction tests |
| Android/iOS platform integration | TBD | 4 | Audio implementations and comparison |
| Accessibility, RTL, and mobile quality | TBD | 4 | Quality-gate report |
| Release lifecycle | TBD | 4 | Internal releases and store package |

## Current course progress

| Module | Status | Passed lessons | Notes |
|---|---|---:|---|
| 00 Orientation and toolchain | Passed | 3/3 | Native hosts traced; Desktop Hot Reload established |
| 01 Kotlin through the domain | In progress | 3/6 | Letter data/value types implemented; enum and sealed-family choices explained |
| 02 Compose foundations | Not started | 0/6 | — |
| 03 KMP boundaries and resources | Not started | 0/4 | — |
| 04 Alphabet vertical slice | Not started | 0/5 | — |
| 05 Session engine | Not started | 0/6 | — |
| 06 Presentation, navigation, Koin | Not started | 0/5 | — |
| 07 Room and progress | Not started | 0/6 | — |
| 08 Audio and platforms | Not started | 0/4 | — |
| 09 Practice and integration | Not started | 0/5 | — |
| 10 Complete alphabet and quality | Not started | 0/5 | — |
| 11 Release and defense | Not started | 0/4 | — |

Current lesson: `01.04 — Collections and higher-order functions`

Published inventory: 9/59 full lesson pages. The remainder of Module 00 and all of Module 01 are ready, so lesson publication is no longer blocking the current or next module.

### Current lesson record

```text
Lesson ID: 01.03
Status: Passed
Date started: 2026-08-20
Date passed: 2026-08-20
Product artifact: Shared starter represents Aleph and Bet as Letter data values with typed LetterId identity and read-only sound lists
Checks run: Desktop Kotlin compilation succeeded after model and call-site changes
Evidence: Implemented data/value classes, explained copy behavior, distinguished object/class payloads, and selected enum versus sealed families from product state shape
Debugging evidence: Corrected mutable-list default and challenged an underspecified stateless Correct case when UI behavior required per-answer identity
Application checkpoint: Local only; app source was not pushed because the configured GitHub remote remained public
```

Most recent passed lesson: `01.03 — Data classes, value classes, enums, and sealed types` on 2026-08-20.

Current task-based evidence:

- Functions and null safety: level 3 — extracts typed pure functions, calls with named arguments, uses a nullable default, and safely handles absent/empty content.
- Kotlin type modeling: level 3 — implements a domain data class and typed value-class identity, reasons about immutable copying, and chooses enum/sealed/object/class shapes from required product state.
- Kotlin declarations and expressions: level 3 — implements read-only local values, type inference, interpolation, and state-derived if expressions in the running application.
- Recomposition reasoning: level 2 — explains that a derived val is initialized again from current observable state rather than reassigned.
- KMP source sets and boundaries: level 3 — distinguishes packages, modules, source sets, and native hosts; correctly places common domain, Android implementation, Swift entry-point, shared-test, and desktop-development code.
- Build/debugging: level 2 — separates an IDE analysis diagnostic from Gradle configuration evidence and uses the wrapper to identify the actual build result.
- Development loop: level 3 — traces Android, iOS, and desktop hosts into shared Compose; distinguishes preview tooling from a real JVM host and from packaged phone targets.
- ViewModels and unidirectional flow: level 2 — explains UI rendering/events and a longer-lived state coordinator.
- Architecture and ownership: level 2 — separates ephemeral UI, durable learner state, and UI-independent learning decisions.
- Room and relational modeling: level 2 — identifies individual attempts as durable facts and a future practice queue as a derived decision.
- Product modeling: correctly notices that guided lessons and practice sessions have different resume and queue requirements.
- Repository ownership: correctly assigns immutable letter facts to Alphabet and learner attempts to Progress.
- Package ownership: explains that cross-feature reuse does not erase a product domain; Progress retains its models, rules, and repository contract rather than becoming generic `common` or `utils` code.
- Package refinement: reserve an explicit design-system owner for truly reusable visual primitives, and avoid overloading KMP's technical `commonMain` vocabulary with a miscellaneous `common` package.
- Coaching contract: confirmed — learner writes core Kotlin application code; coach teaches with bounded examples, reviews and debugs attempts, maintains public course docs, and reveals hints or solutions at the learner's requested level.
- Compose state ownership: level 2 — distinguishes bundled content, temporary visual state, active-session state, and durable Room history; self-corrects when feedback behavior implies a longer-lived owner.
- Next Compose refinement: collecting `StateFlow<UiState>` in a Composable does not make the collected value Compose-local; do not duplicate selected-answer truth with a separate `remember` value.
- State-lifetime exercise: correctly assigns guided-lesson checkpoints and learner history to Room, preferences to DataStore, authored explanations to bundled content, and decorative state to Compose-local ownership.
- Terminology refinement: DataStore is authoritative durable preference storage in this design, not a disposable cache.
- Next refinement: distinguish a repository contract from its Room-backed implementation, and distinguish a ViewModel that coordinates a practice plan from the plain Kotlin policy that builds it.

Current coaching calibration:

- Lead with a concise explanation or worked example, then move immediately to a real attempt.
- Prefer official documentation and written reasoning as the durable reference material.
- Keep isolated experiments brief and connect them to a visible Aleph Bet product outcome.
- Treat loss of visible progress as a signal to diagnose the missing mental model or reassess the task.
- Use AI primarily as a teacher, reviewer, and debugging partner during core learning work.
- Reveal help through learner-requested hint levels rather than an elapsed-time rule.
- Ask for think-aloud reasoning freely; uncertainty does not need to be hidden.
- Divide work into resumable batches because session length will vary around a full-time job.
- Do not inventory React Native, Java, Python, or API recall; assume concepts transfer and calibrate through authentic Kotlin work.
- Use React Native comparisons to explain a delta, not to test dormant framework muscle memory.
- Treat agentic engineering as established background, not curriculum; protect direct Kotlin implementation time.
- Use teach-backs selectively for technical mental models; accept evidence already present in the conversation and avoid redundant meta-assignments.
- Keep lessons product-first. Introduce Gradle and build configuration only when a dependency, target, or observed failure creates a concrete reason.
- Use Desktop Hot Reload for the ordinary shared-UI loop and verify Android/iOS at meaningful checkpoints instead of after every edit.

Current learning priorities:

1. Build direct Kotlin implementation fluency rather than only code-reading familiarity.
2. Make Gradle modules and KMP source sets concrete through the real repository.
3. Introduce explicit ViewModel and unidirectional-flow responsibilities through product behavior.
4. Treat coroutines, `Flow`, and `StateFlow` as high-attention topics with repeated practical use.
5. Assume general Android/iOS application concepts transfer while teaching the KMP mechanisms that expose their boundaries.

Current delivery constraint:

- Target public iOS and Android releases for RevenueCat Shipaton 2026 and its Ship Kotlin Everywhere category.
- Treat September 30, 2026 at 11:45 PM Pacific as the submission deadline, not the preferred store-release date.
- Work toward being publicly live by September 23 to preserve review and submission buffer.
- Include a qualifying RevenueCat-powered purchase or ad path and the required submission assets.
- Prioritize deep understanding of the code on the release path; schedule nonessential enrichment after V1 when necessary.

Current release readiness:

- Apple Developer and Google Play accounts have prior public-release experience.
- Shipaton registration and an active RevenueCat account were verified read-only on 2026-08-18.
- A separate React Native Shipaton submission is already in progress; it is not evidence of Aleph Bet KMP implementation progress.
- Treat Aleph Bet as the KMP learning and Ship Kotlin Everywhere candidate unless the product goal changes explicitly.

## Evidence recorded for each lesson

```text
Lesson ID:
Status:
Date started:
Date passed:
Product artifact:
Checks run:
Independent task:
Teach-back summary:
Exit-ticket result:
Review findings:
Revisions:
Commit:
Skills changed:
Retrieval date:
```

The record should link to private application commits only when the repository is private. Public course documentation must not publish application source or sensitive repository history.

## Assessment types

### Prediction checks

The learner predicts compile behavior, runtime behavior, state lifetime, or dependency direction before observing the result. These reveal mental models quickly.

### Implementation labs

The learner completes a bounded task. Early labs may provide signatures or partial scaffolding; later labs provide acceptance criteria only.

### Independent variations

Every important concept is applied in a case not copied from the demonstration. Examples include adding another letter, creating a new transition, or placing a different platform service.

### Debugging labs

The learner receives or encounters a compiler failure, test failure, lifecycle bug, invalid state, or source-set error and must propose a cause before changing code.

### Teach-backs

The learner explains the concept to an imagined React Native developer. Strong explanations include purpose, ownership, lifetime, dependency direction, and analogy limits.

### Module checkpoints

Module gates combine several lessons in an end-to-end behavior. A module does not pass merely because each isolated lesson once worked.

### Final architecture defense

The coach presents change requests and failure scenarios. The learner explains impact across product behavior, screen state, domain logic, data, DI, source sets, testing, and delivery.

## Review rubric

Each submitted implementation is reviewed across the dimensions that apply:

| Dimension | Passing question |
|---|---|
| Correctness | Does behavior meet the acceptance criteria and edge cases? |
| Understanding | Can the learner explain why it works? |
| Kotlin fluency | Are types, nullability, collections, and language features used clearly? |
| State ownership | Does each value live for the correct lifetime? |
| Dependency direction | Do presentation, domain, data, and platform code depend appropriately? |
| Test quality | Do tests prove product rules rather than mirror implementation? |
| Cross-platform behavior | Was the required target set actually verified? |
| Accessibility and UX | Is the interaction usable beyond the happy-path simulator? |
| Simplicity | Is every abstraction justified by a present need? |

## Retrieval schedule

Important concepts should be recalled:

- at the beginning of the next lesson;
- approximately one module later;
- when the same concept appears in a different layer;
- during the final defense.

A retrieval miss does not erase prior progress. It changes the skill or lesson status to `Revisit` and creates a targeted exercise.

## Coach calibration notes

The course should become harder in response to success:

- reduce code shown before an attempt;
- ask for more prediction and design;
- widen the independent variation;
- introduce realistic failure cases;
- require the learner to choose the test strategy;
- ask the learner to review the coach's proposed design.

It should become more supportive in response to a specific gap, not by replacing the learner's whole implementation.
