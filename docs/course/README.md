# Build Aleph Bet V1: a personalized KMP course

Status: Course map ready; 1 of 59 full lesson pages published; instruction not started

Last updated: 2026-08-18

## Course promise

Build and prepare the alphabet-only Aleph Bet V1 for Android and iOS while gaining enough Kotlin, Compose Multiplatform, KMP, Room, coroutines, testing, and mobile architecture fluency to explain and extend the application without following a tutorial blindly.

The application is the curriculum. Every technical concept enters when the product creates a reason to learn it.

## Who this is designed for

This is not a generic beginner programming course. It assumes the learner:

- has shipped or substantially built React Native and Expo applications;
- understands components, props, hooks, navigation, backend calls, and TypeScript;
- learns well from complete project builds;
- is new enough to Kotlin and KMP that language, tooling, lifecycle, and build-system assumptions must be made explicit;
- wants active coaching and evaluation, not only generated code.

Pocket Globe/Globe Game and the vocabulary app are the primary bridge projects because they best represent code the learner remembers building directly. Bearywell and WaveCall may still supply familiar examples, but they are not treated as evidence that a concept is already mastered. When a KMP concept is introduced, the lesson should identify the nearest React Native pattern, where the analogy holds, and where it breaks.

## Course documents

Read these in order:

1. [Teaching method and coaching contract](method.md)
2. [How to take the course](how-to-take-the-course.md)
3. [Complete course syllabus](syllabus.md)
4. [Assessment and progress system](assessment-and-progress.md)
5. [Lesson-page template](lesson-template.md)
6. [Lesson 00.01: Baseline and course map](lessons/00-01-baseline-and-course-map.md)

Supporting product and technical decisions remain in the [product roadmap](../product/alphabet-v1-roadmap.md) and [architecture index](../architecture/README.md).

## Course structure

The course map contains 12 modules and 59 planned lessons. That does not mean 59 complete lesson pages already exist: the syllabus defines all 59 outcomes, while full lesson pages are published in rolling module-sized packs so they can respond to demonstrated strengths and gaps. The publication workflow and current inventory are explicit in [How to take the course](how-to-take-the-course.md).

Most lessons should take 30–90 focused minutes, but progress is mastery-based rather than time-based. A realistic complete journey is roughly 55–80 hours of explanation, implementation, testing, debugging, review, and independent work.

That estimate is intentionally longer than an eight-hour video. Video runtime excludes pauses, mistakes, independent assignments, cross-platform verification, retrieval practice, and the work required to demonstrate understanding.

| Module | Subject | Product milestone | Status |
|---|---|---|---|
| 00 | Orientation and toolchain | Shared starter runs; baseline recorded | Not started |
| 01 | Kotlin through the alphabet domain | Valid three-letter domain model and tests | Not started |
| 02 | Compose foundations | Hard-coded accessible three-letter explorer | Not started |
| 03 | KMP boundaries and resources | Real resources load on Android and iOS | Not started |
| 04 | Alphabet vertical slice | Bundled repository, explorer, and letter detail | Not started |
| 05 | Learning session engine | Tested lesson queue and exercise transitions | Not started |
| 06 | Presentation, navigation, and Koin | Lesson session driven through a ViewModel | Not started |
| 07 | Room and durable progress | Attempts, progress, resume, and migrations | Not started |
| 08 | Audio and platform integration | Reviewed audio works across both targets | Not started |
| 09 | Practice and app integration | Complete three-letter vertical slice | Not started |
| 10 | Complete alphabet and quality | Full alphabet, final forms, accessibility, QA | Not started |
| 11 | Release and architecture defense | Store-ready candidate and final defense | Not started |

The [assessment document](assessment-and-progress.md) is the authoritative status record once instruction begins.

## The default learning loop

```text
Retrieve prior knowledge
        -> learn one mental model
        -> predict behavior
        -> guided implementation
        -> independent variation
        -> tests and debugging
        -> teach it back
        -> code review
        -> commit
        -> revisit later
```

The learner writes the core application code. The coach supplies the lesson, the mental model, carefully sized examples, acceptance criteria, hints, review, tests or test plans, and debugging support.

## What the coach does

- Maintains and publishes the course documentation.
- Opens each lesson with retrieval questions and a clear product outcome.
- Explains Kotlin using React Native comparisons without forcing false equivalences.
- Demonstrates only the smallest example needed to reveal a new concept.
- Gives tasks and acceptance criteria before showing a solution.
- Reviews the learner's implementation and asks for reasoning.
- Runs or helps run relevant tests and interprets compiler errors.
- Records mastery evidence and adapts later lessons.
- Introduces production concerns throughout the build.

## What the learner does

- Predicts before running code.
- Types and designs the core Kotlin implementation rather than transcribing a finished class.
- Explains important choices in plain language.
- Attempts assignments before requesting a solution.
- Uses the hint ladder instead of jumping immediately to complete code.
- Writes or completes tests for important behavior.
- Fixes review findings and compiler errors.
- Maintains the private application history through small lesson commits.

## What may be copied

Typing is valuable when the code expresses the concept being learned. Copying is acceptable when transcription adds no useful understanding.

Usually type or design yourself:

- domain models;
- state transitions;
- Composable parameters and UI state;
- repository contracts;
- ViewModel actions;
- DAO queries after their behavior is understood;
- tests and fixes.

Usually safe to copy with explanation:

- dependency coordinates and Gradle plugin configuration;
- generated schemas or generated code;
- long resource paths;
- store metadata templates;
- boilerplate supplied by official project generators.

## How to start a lesson

Say:

> Start lesson 00.01.

The coach will use that lesson page as the agenda, ask the opening diagnostic questions, and proceed interactively. Do not read future solution material during an active attempt.

## Course completion standard

Shipping the app is necessary but insufficient. The course is complete when the learner can:

- trace a user action from Composable to durable state and back;
- explain why each important state value has its owner and lifetime;
- implement and test a new session rule without copying an existing solution;
- place new code in the correct package and source set;
- model and migrate a Room schema safely;
- diagnose common Compose, coroutine, DI, and KMP boundary failures;
- run and validate both Android and iOS targets;
- defend the V1 architecture and name evidence that would justify changing it;
- produce a release candidate and a credible test report.

## Source inspiration

The delivery format is inspired by—not copied from—Vadim Savin's [React Native and Supabase masterclass](https://www.youtube.com/watch?v=rIYzLhkG9TA), its [public project overview](https://www.notjust.dev/projects/food-ordering), and the exact [public Notion masterclass board](https://notjust.notion.site/React-Native-Supabase-Masterclass-47a69a60bc464c399b5a0df4d3c4a630). This is an independent curriculum for a different application, technology stack, and learner.
