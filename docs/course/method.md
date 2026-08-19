# Teaching method and coaching contract

Status: Working course method

Last updated: 2026-08-18

## The answer to “should I type the classes you give me?”

Not as the default.

Transcribing a complete class can create familiarity, but it often trains recognition rather than recall. The learner feels that the code makes sense while it is visible, then cannot reconstruct the design from a blank file.

The course uses **gradual release of responsibility**:

```text
Coach models a small new idea
        -> coach and learner build one case together
        -> learner builds the real app case
        -> learner explains and extends it independently
```

Early Kotlin lessons contain more demonstration. Later lessons provide a product brief, interfaces or acceptance tests, and constraints while the learner chooses the implementation.

Approximate responsibility shift:

| Course stage | Coach demonstration | Guided work | Independent work |
|---|---:|---:|---:|
| Modules 00–02 | 30% | 50% | 20% |
| Modules 03–06 | 15% | 45% | 40% |
| Modules 07–09 | 10% | 30% | 60% |
| Modules 10–11 | 5% | 20% | 75% |

These are teaching ratios, not timers.

## What works in the referenced masterclass

The linked course uses several strong instructional devices:

1. **One concrete product narrative.** The learner builds a food-ordering application rather than studying disconnected APIs.
2. **Three visible modules.** UI, backend, and production create a comprehensible journey.
3. **A prepared asset bundle.** Images and dummy data remove irrelevant searching and allow the learner to focus on development.
4. **Just-in-time concepts.** Components, props, lists, navigation, state, CRUD, subscriptions, payments, and notifications enter when the application needs them.
5. **Incremental visible output.** A learner repeatedly sees the application become more capable.
6. **A lesson board.** Lessons carry module and status metadata, making progress visible.
7. **Lesson objectives and sequential instructions.** Representative pages begin with what will be learned, explain the idea, then provide numbered implementation steps and focused snippets.
8. **Assignments before solutions.** The UI module includes independent assignments followed by solution walkthroughs.
9. **Production belongs to the course.** Polish, environment setup, payments, notifications, and deployment are not treated as somebody else's problem.
10. **Reference material accompanies the walkthrough.** The written guide can be revisited without scrubbing through hours of video.

Those observations come from the public [video chapter outline](https://www.youtube.com/watch?v=rIYzLhkG9TA), [project page](https://www.notjust.dev/projects/food-ordering), and public Notion guide inspected on 2026-08-18.

## What this course adds

The masterclass optimizes for a complete guided build in a finite video. A live personalized course can demand stronger evidence of transfer:

- prediction before execution;
- retrieval questions at the start of later lessons;
- explicit React Native comparison and analogy limits;
- test-first work for learning and progress rules;
- compiler-error reading rather than immediate correction;
- graduated hints;
- independent variations not shown in the demonstration;
- architecture teach-backs;
- cross-platform lifecycle checks;
- adaptive pacing based on observed gaps;
- review and revision before a lesson is marked passed.

## The lesson protocol

Every full lesson follows the same rhythm.

### 1. Retrieval warm-up

Two to five questions recall prior lessons without consulting notes. This makes forgetting visible and strengthens retrieval.

Examples:

- Why does `remember` not own lesson progress?
- Which source set can import AVFoundation?
- What is the difference between a content DTO and a domain model?

### 2. Product reason

State the application problem before naming the API. For example:

> Practice needs choices to remain stable while the learner answers, so we need a session-state owner whose lifetime exceeds recomposition.

This prevents “learn StateFlow because StateFlow is on a checklist.”

### 3. Mental model and RN bridge

Explain the concept in plain language, map it to the nearest relevant React Native pattern from Pocket Globe/Globe Game, the vocabulary app, or another familiar project, and identify where that comparison fails.

### 4. Prediction

Before running code, the learner predicts:

- whether it compiles;
- what value or UI appears;
- when recomposition occurs;
- which state survives an interruption;
- which dependency direction is legal.

### 5. Minimal demonstration

The coach may show a deliberately small example that isolates the new syntax or behavior. It should not be the complete production implementation the learner is about to build.

### 6. Guided lab

Coach and learner implement the first case together. The coach asks questions and gives the next constraint rather than dumping the finished file.

### 7. Independent build

The learner implements a variation or the production version from acceptance criteria. The lesson page does not reveal the full solution above the task.

### 8. Test and debug

Run the smallest relevant checks. The learner reads the failure first and proposes a cause before the coach explains it.

### 9. Teach-back

The learner explains the new concept as if onboarding a React Native developer. A working app with an incorrect mental model is not yet mastery.

### 10. Review and revision

The coach reviews correctness, Kotlin idioms, state ownership, boundaries, tests, and unnecessary abstraction. The learner makes the revisions.

### 11. Commit and progress record

Once the lesson evidence passes, create a small private application commit and update the public course progress document without publishing application code.

### 12. Delayed retrieval

One or more later lessons revisit the concept in a different context.

## The assistance ladder

When blocked, use the least revealing intervention that restores progress:

1. Restate the product behavior.
2. Ask a focusing question.
3. Point to the relevant mental model or official reference.
4. Give a conceptual hint.
5. Give pseudocode or a type signature.
6. Provide a partial skeleton with meaningful blanks.
7. Pair line by line while the learner makes each decision.
8. Show a complete solution only after a real attempt, then require an explanation and independent variation.

The learner can request a level directly, for example:

> Give me hint level 3, not the code.

## Default code-ownership rule

The conversation, private application repository, and public documentation repository have different jobs. The complete interaction and publication workflow is in [How to take the course](how-to-take-the-course.md).

During course mode:

- the learner writes feature, domain, presentation, and persistence behavior;
- the coach writes and publishes course documentation;
- the coach may inspect files, run read-only diagnostics, run tests, and review diffs;
- the coach does not silently implement the assignment;
- configuration boilerplate can be supplied directly after its purpose is explained;
- exercise scaffolding or failing tests are added only when the learner explicitly starts that lab or requests the setup;
- a request such as “take over and fix this” temporarily switches from teaching mode to implementation mode, and the coach explains what learning opportunity is being skipped.

## The Room principle

Room itself is not the enduring lesson. The transferable material is:

- translating product behavior into durable facts;
- relational modeling and keys;
- separating database entities from domain and UI models;
- queries and observable data;
- transactions and consistency;
- migrations and backward compatibility;
- testing persistence;
- deciding what should not be stored.

Room is the concrete KMP implementation that makes those principles real. A later project could replace Room while preserving most of the reasoning.

## The backend principle

Aleph Bet V1 intentionally has no backend. Adding one for the sake of matching a “full-stack” label would weaken both the product and the course.

The app is still end-to-end:

```text
bundled curriculum
    -> domain models and learning rules
    -> Compose presentation
    -> Room learner history
    -> platform audio
    -> Android and iOS release artifacts
```

This supplies sufficient depth in modeling, asynchronous state, persistence, platform boundaries, testing, and release engineering.

## Review style

Reviews lead with evidence:

- the exact behavior that passes or fails;
- the dependency or state-lifetime rule involved;
- the smallest correction;
- why the correction matters beyond style;
- a question that checks whether the learner can generalize it.

The coach should not rewrite a working solution merely to make it look like the coach's preferred code. Kotlin idioms are taught when they improve clarity, correctness, safety, or maintainability.

## Adaptive branching

The syllabus is a map, not a rigid script.

- If Kotlin syntax is the blocker, insert a small language lab without changing the product milestone.
- If the learner demonstrates mastery, compress explanation and increase the independent challenge.
- If an architecture abstraction has no real need in the slice, postpone it.
- If Android and iOS behave differently, stop feature expansion and turn the difference into a platform lesson.
- If the product decision is unresolved, do not disguise it as a technical exercise.

## Definition of a passed lesson

A lesson passes only when its stated evidence exists. Depending on the lesson, that may include:

- behavior works on the required target or targets;
- tests pass and test intent is explained;
- the learner completes the independent variation;
- the learner answers the exit ticket;
- review findings are resolved;
- the learner can name ownership, lifetime, and dependency direction;
- the change is committed with a meaningful message.

“I followed the code and it runs” is progress, but it is not the final standard.
