# Assessment and progress

Status: Lesson 03.01 in progress — Module 02 Android/iOS smoke checks carried to the Module 03 native checkpoint

Last updated: 2026-08-22

## Why the course tracks evidence

Finishing a lesson page does not prove that a skill transferred. Progress is based on observable evidence: prediction, implementation, tests, debugging, explanation, and later recall.

The purpose is not school-style grading. It is to answer honestly:

- What can the learner recognize?
- What can the learner explain?
- What can the learner build with guidance?
- What can the learner build independently?
- What can the learner debug and teach?

## Product evidence and mastery evidence

The record tracks two related but different results:

- **Product evidence:** the requested capability exists and passes its required checks, regardless of whether it was implemented in Learn, Pair, or Ship mode.
- **Mastery evidence:** the learner predicted, implemented, debugged, explained, or transferred the underlying skill at the recorded level.

Coach-completed code can satisfy a product gate. It does not automatically create mastery evidence, but it also does not erase evidence already demonstrated in an earlier representative task.

Apply the [proof-once rule](method.md#proof-once-and-retrieval-rule): do not keep a lesson open solely because the learner did not personally type a structurally equivalent component, modifier chain, content row, or test case after the relevant skill was already established. Defer evidence only when the skipped task was the remaining authentic proof of a new core skill.

## Lesson statuses

| Status | Meaning |
|---|---|
| Not started | No diagnostic or implementation attempt yet |
| In progress | Lesson is active |
| Submitted | Learner has presented the required evidence for review |
| Needs revision | Specific gaps remain; feedback is recorded |
| Passed | Applicable product and mastery evidence are met; repeated proof is not required |
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

Course completion does not require level 5 in every library API or personal authorship of every production line. It does require level 4 or 5 in the core skills that define the app.

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
| 01 Kotlin through the domain | In progress | 4/6 | Collection foundations demonstrated; repository/mapping and test checkpoint deferred to authentic product boundaries |
| 02 Compose foundations | In progress | 2/6 | Implementation is not repeated; combined Android/iOS visual smoke checks remain and are scheduled with Lesson 03.04 native launches |
| 03 KMP boundaries and resources | In progress | 0/4 | Lesson 03.01 started; learner owns placement reasoning and one representative content move |
| 04 Alphabet vertical slice | Not started | 0/5 | — |
| 05 Session engine | Not started | 0/6 | — |
| 06 Presentation, navigation, Koin | Not started | 0/5 | — |
| 07 Room and progress | Not started | 0/6 | — |
| 08 Audio and platforms | Not started | 0/4 | — |
| 09 Practice and integration | Not started | 0/5 | — |
| 10 Complete alphabet and quality | Not started | 0/5 | — |
| 11 Release and defense | Not started | 0/4 | — |

Current lesson: `03.01 — Targets, source sets, and dependency reach`

Published inventory: 24/59 full lesson pages. Complete packs for Modules 02–04 are published so product implementation can continue without live lesson improvisation. Lessons 01.05–01.06 remain published reference material and their repository/mapping/testing outcomes are retrieved in Module 04 when real bundled content creates those boundaries.

### Active lesson record

```text
Lesson ID: 03.01
Status: In progress
Date started: 2026-08-22
Work mode: Learn for placement reasoning and one representative move; Pair or Ship for equivalent remaining content moves and mechanical setup
Product outcome: Temporary alphabet content moves out of App() into shared Alphabet-owned content without changing explorer behavior
Mastery evidence required: Predict target visibility, choose package/source-set placement, implement one representative move, and distinguish module, source set, and package
Proof-once adaptation: Do not repeat completed Module 02 component, state, grid, semantics, content-row, or UI-test implementation
Module 02 carry-forward: Run the outstanding Android/iOS visual smoke checks during the Lesson 03.04 native launches and record the Module 02 UI evidence separately from Module 03 resource evidence
Application checkpoint: Local only; do not push application source while the configured GitHub remote remains public
```

### Previous lesson record

```text
Lesson ID: 02.01
Status: Needs revision
Date started: 2026-08-20
Date passed: —
Guided product artifact: Feature-owned LetterCard renders Aleph, Bet, and Gimel; observable Compose state reveals and hides real letter content
Checks run: Desktop Hot Reload verified the interaction; desktop Kotlin compilation succeeded
Evidence so far: Extracted a composable with explicit domain input and replaced template behavior with real letter visibility
Evidence still required: Concise recomposition/ownership explanation
Adaptation: The learner made a real `AlphabetHeader` attempt and later correctly challenged and removed the unjustified one-use abstraction. Under the proof-once rule, a second production component is no longer required; only the unresolved mental-model evidence remains.
Application checkpoint: Local only; app source was not pushed because the configured GitHub remote remained public
```

### Current lesson record

```text
Lesson ID: 02.02
Status: Passed
Date started: 2026-08-20
Date passed: 2026-08-21
Product artifact: Responsive Material LetterCard with caller modifier, internal spacing, theme shape, tonal elevation, and semantic typography
Checks run: Modifier-order visual comparison, desktop Kotlin compilation, and formatting check
Independent task: Styled header attempt reviewed; header inlined after the learner correctly challenged its unjustified one-use abstraction
Understanding evidence: Correctly predicted modifier order, distinguished parent-owned inter-card spacing from component-owned padding, and selected a fresh internal modifier chain
Review findings: Progress typography competed with title; redundant header boundary added ceremony
Revisions: Header typography was calmed and inlined; desktop compilation passed
```

### Previous lesson record — deferred evidence

```text
Lesson ID: 02.03
Status: Needs revision
Date started: 2026-08-21
Product artifact: Stateless interactive LetterCard with focused previews
Evidence so far: Parent-owned card callback, independently designed audio callback, verified Aleph preview, function-value explanation, desktop and Android compilation
Debugging evidence: Diagnosed a blank Run Preview as card content hidden beneath PreviewActivity's action bar using emulator process logs and accessibility hierarchy
Evidence still required: Explicit runtime confirmation that nested card and audio clicks invoke only their respective callbacks; the Bet preview is waived because the running app already covers its two-sound branch
```

### Most recent passed lesson record

```text
Lesson ID: 02.04
Status: Passed
Date started: 2026-08-22
Date passed: 2026-08-22
Product artifact: Parent-owned selected-letter state rendered through stateless cards
Checks run: Hide/show child lifetime check, desktop process restart boundary, desktop and Android compilation, formatting check
Independent task: Clear-selection control returns the single nullable selected ID to its initial state
Understanding evidence: Correctly predicted recomposition, child removal, parent removal, process relaunch, reinstall, and Room boundaries; identified effects as the Compose counterpart to lifecycle-controlled `useEffect` work and placed active practice-session state in a ViewModel
Review findings: Clear label and formatting only
Revisions: Mechanical cleanup completed; no state, effect, or persistence ownership changes required
Exit ticket: Passed after a definition-first correction clarified effects as lifecycle-controlled bridges from composition to external or asynchronous work
```

Most recent passed lesson: `02.04 — Remembered state, saveable state, and effects` on 2026-08-22.

Current task-based evidence:

- Functions and null safety: level 3 — extracts typed pure functions, calls with named arguments, uses a nullable default, and safely handles absent/empty content.
- Kotlin type modeling: level 3 — implements a domain data class and typed value-class identity, reasons about immutable copying, and chooses enum/sealed/object/class shapes from required product state.
- Collections: level 3 — uses an ordered read-only letter list, sorting, iteration, and typed lookup reasoning; advanced transformations are deferred for retrieval in real features.
- Compose layout and components: level 3 — implements a caller modifier contract, separates external placement from internal spacing, uses Material roles, predicts modifier order, and removes an unjustified component boundary after review.
- Compose callbacks and previews: level 3 — passes function values through stateless components, independently adds a distinct audio event, keeps platform behavior outside presentation, and diagnoses preview-host layout; nested-click runtime confirmation remains deferred.
- Compose state and effects: level 3 — independently owns nullable selection in the parent, derives card visuals without duplicated state, verifies composition/process lifetimes, clears synchronously without an effect, and rejects persistence for temporary UI selection.

### Fresh-task handoff for Module 02

Continue Module 02 from the recorded evidence rather than repeating completed layout, callback, or state work. Lessons 02.01 and 02.03 retain narrow evidence gaps; Lessons 02.02 and 02.04 passed through authentic implementation and review. Lesson 02.05 is submitted with its remaining native checks combined into Lesson 02.06.

Lesson 02.05 deferral resolution: Semantics are now inspected through Lesson 02.06 shared UI tests. Android/iOS visual smoke checks remain part of the combined Module 02 checkpoint.

Lesson 02.05 evidence so far: The learner independently replaced the temporary keyed lazy column with a three-column `LazyVerticalGrid`, scoped RTL to the grid without reversing source data, used a responsive aspect ratio for equal card height, aligned variable card content with weighted remaining space, and confirmed selection, clearing, audio, RTL order, and alignment at runtime. Desktop and Android compilation pass. Semantics are covered by Lesson 02.06 shared UI tests; native Android/iOS visual smoke checks remain.

Lesson 02.05 assessment: Teach-back, grid behavior, stable keys, RTL, and semantics evidence pass. The lesson is submitted until the combined native Android/iOS visual smoke checks are recorded in Lesson 02.06.

Lesson 02.06 evidence so far: The version-matched shared Compose UI-test harness passes on Desktop. Bet's glyph/name/multiple-sounds branch and Aleph's empty-sounds branch are tested, and the card callback is invoked through the merged semantics node. The learner and coach classified compile-time API, incompatible Android host-environment, and merged-semantics exact-text failures before correction. Android host tests now use a separate source-set tree while Android device tests retain shared UI tests.

Lesson 02.06 Dalet checkpoint: At the learner's explicit request, the coach added Dalet to the authored collection, updated progress, and added a screen-level assertion that clicks Start learning and observes Dalet through visible semantics. Desktop compilation/UI tests, Android compilation, and formatting checks pass. The product evidence passes. Under the proof-once rule, typing Dalet is not required as a second proof of the already demonstrated card/grid/content pattern; the learner still owns interpretation of the screen-test behavior.

Lesson 02.06 retrieval note: The learner passed the Module 02 teach-back with small corrections, then requested direct answers for the remaining exit-ticket items. Do not count the coach-supplied exit answers as independent recall evidence.

### Module 02 native-check carry-forward

The combined Android/iOS visual smoke checks remain outstanding. They are verification debt, not a reason to repeat completed Module 02 implementation.

Handle them during Lesson 03.04, when both native hosts are already launched for the cross-platform resource checkpoint. On each host, record Module 02 UI evidence separately: the responsive three-column grid renders in RTL order without reversing source data, selection and clearing still work, audio and card actions remain distinct, and representative cards stay aligned. Then record the Module 03 font/resource evidence as its own checkpoint. Resource success does not substitute for the Module 02 UI smoke checks. If either native UI check fails, review and fix that finding before marking Lessons 02.05–02.06 and Module 02 passed.

### Module 03 handoff

Lesson 03.01 begins from the existing local application state. The learner owns the source-set/package placement reasoning and one representative move. After that placement rule is demonstrated, equivalent remaining content movement and mechanical setup may use Pair or Ship mode. No completed Module 02 component, state, grid, semantics, content-row, or shared UI-test work is repeated.

Teaching cadence requirement: Use the published lesson as an agenda and never skip a mastery-critical activity silently. Sections may be explicitly compressed, combined, waived, or moved to Pair/Ship mode when prior evidence already covers them or repetition adds little learning value. Record material adaptations. Define each new technical or accessibility term in plain language before using it in a question or implementation task; do not assume React Native coverage of an analogous concept.

Current application state:

- `Letter`, `LetterId`, Aleph, Bet, Gimel, and Dalet exist locally.
- `LetterCard` is a responsive Material `Surface` with a caller modifier, internal spacing, theme shape, tonal elevation, and semantic typography.
- `LetterCard` emits separate parent-owned card and audio callbacks and remains stateless.
- The Aleph preview renders after a preview-only centering workaround; a separate Bet preview is waived because the running app already covers its two-sound branch.
- The one-use `AlphabetHeader` was removed after review; its styled content is inline in `App()`.
- `App()` owns remembered nullable selected-letter state, derives each card's selected Boolean, and provides a clear-selection action.
- Desktop Hot Reload works; desktop and Android Kotlin compilation pass.
- Application source remains local and must not be pushed while its GitHub remote is public.

Resume point:

1. Continue Lesson 03.01 from its product outcome and retrieval warm-up.
2. Do not repeat completed modifier, callback, preview, state, grid, Dalet, or shared UI-test implementation work.
3. Keep the Module 02 Android/iOS smoke checks visible and execute them alongside the Lesson 03.04 native launches, with separate evidence for each module.
4. Keep source-set/package placement reasoning and one representative content move in Learn mode.
5. Move equivalent remaining content moves and mechanical setup to Pair/Ship mode after the placement rule is demonstrated.

Recommended fresh-task prompt:

```text
Continue the Aleph Bet KMP course with Module 03 at Lesson 03.01. Read docs/course/method.md, docs/course/how-to-take-the-course.md, docs/course/v1-completion-matrix.md, docs/course/assessment-and-progress.md, and docs/course/lessons/03-01-targets-source-sets-dependencies.md completely. Preserve completed Module 02 evidence. Its Android/iOS smoke checks remain scheduled alongside the Lesson 03.04 native launches and must be recorded separately from resource evidence. Begin from the Lesson 03.01 product outcome and retrieval warm-up; the learner owns placement reasoning and one representative implementation.
```
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
- Coaching contract: learner owns new core decisions and representative Kotlin implementation; coach teaches, reviews, debugs, maintains course docs, and may complete proven repetition or mechanical release work in explicit Pair/Ship mode.
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
- Use Pair/Ship mode for proven repetition and mechanical release work; protect direct implementation time for new core skills.
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
- Treat that qualifying path as the conditional release overlay in the V1 completion matrix, not as a general payments curriculum or a paywall on alphabet reference content.
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
Work mode:
Product artifact:
Product evidence:
Mastery evidence:
Checks run:
Independent task or proof-once adaptation:
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

The learner completes a bounded representative task when new mastery evidence is needed. Labs may provide signatures, partial scaffolding, or Pair-mode integration according to risk and prior evidence; later course position alone does not require more typing.

### Independent variations

Every core skill must eventually transfer to a case not copied from the demonstration. That evidence may come in the same lesson or a later authentic feature. Do not require a separate variation for every lesson or repeat a skill already proven.

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
- widen the transfer challenge when new evidence is still needed;
- introduce realistic failure cases;
- require the learner to choose the test strategy;
- ask the learner to review the coach's proposed design.

It should become more supportive in response to a specific gap, not by replacing the learner's whole implementation.
