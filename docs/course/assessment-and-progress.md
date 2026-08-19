# Assessment and progress

Status: Baseline in progress

Last updated: 2026-08-18

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
| Kotlin syntax and null safety | TBD | 4 | Domain and independent variation |
| Kotlin type modeling | TBD | 5 | Session model and architecture defense |
| Collections and functional transformations | TBD | 4 | Alphabet mapping and queue building |
| Compose mental model | TBD | 4 | UI implementation and recomposition diagnosis |
| Compose state and effects | TBD | 5 | Session UI and lifecycle debugging |
| KMP source sets and boundaries | TBD | 5 | Platform placement challenge and audio integration |
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
| 00 Orientation and toolchain | In progress | 0/3 | Lesson 00.01 active |
| 01 Kotlin through the domain | Not started | 0/6 | — |
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

Current lesson: `00.01 — Baseline and course map`

### Current lesson record

```text
Lesson ID: 00.01
Status: In progress
Date started: 2026-08-18
Evidence: Architecture and state-ownership diagnostic in progress
```

Current task-based evidence:

- ViewModels and unidirectional flow: level 2 — explains UI rendering/events and a longer-lived state coordinator.
- Architecture and ownership: level 2 — separates ephemeral UI, durable learner state, and UI-independent learning decisions.
- Room and relational modeling: level 2 — identifies individual attempts as durable facts and a future practice queue as a derived decision.
- Product modeling: correctly notices that guided lessons and practice sessions have different resume and queue requirements.
- Repository ownership: correctly assigns immutable letter facts to Alphabet and learner attempts to Progress.
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
