# Architecture answers and decision queue

Status: Proposed answers for discussion

Last updated: 2026-08-18

This document is the answer key for the architecture exercises and the working queue of decisions that still shape the three-letter vertical slice. An answer marked **Accepted** is part of the current architecture. An answer marked **Proposed** is the recommended starting point and can change after discussion or evidence from the slice. **Spike required** means the architecture boundary is decided, but a library or implementation choice still needs a small experiment.

## What "queue" means

A queue is the ordered work for one learning session. It answers: **what should the learner see next?** It is not a screen, ViewModel, Room table, or necessarily Kotlin's mutable `Queue` collection.

For this app, a session can be thought of as:

```text
Session plan: [introduce alef, ask alef, introduce bet, ask bet, mixed review]
Current index:                         ^
```

The session engine reads the current item, evaluates an action, updates progress when appropriate, and advances the index. A plain `List<SessionItem>` plus a current index is likely clearer than a mutable queue because it makes the session easy to inspect and test.

There are two queue producers:

### Lesson queue

The bundled curriculum defines the lesson steps and their order. Starting lesson 1 turns those authored definitions into a session plan. Every learner gets the same teaching sequence, although answer choices may be shuffled.

### Practice queue

Practice asks a selection rule to build a session plan from introduced letters and learner progress. Letters needing review should be selected before letters currently doing well. Practice reuses the same session engine and exercise types as lessons; it does not create a second engine.

```text
Bundled lesson definition --------> lesson queue ----\
                                                     > session engine -> UiState
Room progress -> practice policy -> practice queue --/
```

Queue construction belongs in plain Kotlin domain logic. The ViewModel starts and coordinates a session, while Compose renders only the current session state.

For V1, guided lessons resume at the next meaningful step after interruption. Persist the lesson ID, the next step index, and a curriculum version. Do not persist animation state, a pressed button, selected feedback, or the entire in-memory queue. Practice sessions can restart with a newly generated queue.

## Current architecture answers

| Question | Working answer | Status |
|---|---|---|
| How much of a lesson survives process death? | Resume a guided lesson at the next meaningful step. Persist `lessonId`, `nextStepIndex`, and curriculum version; rebuild the session from authored content. Do not restore a half-answered card. | Proposed |
| Do we retain individual attempts? | Retain graded attempts and also maintain per-concept summary progress in the same Room transaction. Omit response time until a real product rule uses it. | Proposed |
| Which audio library do we use? | Common code owns a small `AudioPlayer` contract. Compare candidate playback implementations on real Android and iOS devices before selecting one. | Spike required |
| How general is the lesson-step model? | Start with a small sealed set of explicit step types for introduction, multiple choice, and recap. Do not build a generic content-rendering framework. | Proposed |
| What moves a letter to `SOLID`? | Treat `SOLID` as an app progress signal, not a mastery claim. A starting rule is three correct retrievals across at least two sessions and two exercise types; a later incorrect response returns it to `PRACTICING`. | Proposed |
| When should Gradle modules be split? | Keep one shared module through the vertical slice and alphabet release. Reconsider only after profiling shows painful build isolation, recurring boundary violations, independent reuse, or multiple owners. | Accepted |
| Which dependency-injection style do we use? | Koin Annotations with constructor injection and the Koin Compiler Plugin. Tests instantiate subjects directly with fakes. | Accepted |
| Where does curriculum live? | Reviewed JSON and Compose resources bundled with the app. Room stores learner activity, not copies of immutable curriculum. | Accepted |

## Product decisions that affect architecture

These are product answers first. Architecture should implement them without pretending to prove their educational quality.

| Question | Recommended V1 answer | Status |
|---|---|---|
| Alphabetical or confusion-aware lessons? | Use a hybrid: preserve enough alphabetic order to build a familiar mental map, but separate or deliberately contrast commonly confused forms during review. Final forms stay attached to their base letters. | Proposed |
| Full letter route or sheet? | Use a full detail route. The content, audio controls, accessibility behavior, and final-form relationships justify a stable destination; the grid remains a lightweight overview. | Proposed |
| How do we describe alef and ayin? | Do not assign them a misleading English vowel sound. Explain briefly that their consonantal value may be weak or absent in modern speech and demonstrate them in reviewed context audio. Final wording needs content review. | Proposed |
| How much niqqud appears in V1? | Explain once that dots and marks can indicate vowels in learning examples. Allow pointed names or samples where needed, but do not teach, name, quiz, or track niqqud in the alphabet release. | Proposed |
| Are practice sessions resumable? | No. Persist the completed attempts and progress updates, then create a fresh short practice queue next time. Guided lessons are resumable. | Proposed |

## State-lifetime exercise: answer key

The important question is not merely where a value is displayed. It is who owns the truth and how long that truth must survive.

| Value | Owner and lifetime | Why |
|---|---|---|
| Current letter glyph | Bundled curriculum, projected into UI state | The glyph is authored content; the screen merely renders it. |
| Whether the audio button is pressed | Compose-local state | It is temporary visual interaction state. Actual playback state belongs to the audio service or ViewModel when behavior depends on it. |
| Shuffled answer choices | ViewModel/session state | Choices must remain stable while the learner answers and across recomposition or configuration change. They do not need Room persistence. |
| Lifetime correct count for `bet` | Room | It is durable learner history used across screens and launches. |
| Explanation of bet/vet | Bundled curriculum | It is reviewed, immutable content shipped with the app. |
| Current step in an unfinished lesson | ViewModel while active; Room checkpoint at meaningful boundaries | The active session owns it, but the next safe step must survive the interruption promise. |
| Whether the feedback card is expanded | Compose-local state | Expansion is visual unless it changes whether the learner may advance. Product behavior would move that part into UI/session state. |
| Last-practiced timestamp for `bet` | Room | Practice selection needs it after the current process or session ends. |

## React Native translation

If this were React Native, the queue might begin as an array held by a reducer or Zustand store. In this KMP design:

- `SessionPlan` and the transition rules are plain Kotlin domain objects;
- the ViewModel plays the role of the screen-level store;
- `StateFlow<UiState>` is the observable state consumed by Compose;
- Room holds durable checkpoints and progress;
- Koin constructs the graph but does not own the learning rules.

The useful separation is the same: rendering should not decide the next exercise, and persistence should not dictate the lesson experience.

## Next discussion order

1. Approve or revise the resume promise.
2. Approve or revise attempt storage.
3. Define the exact learning-stage transitions.
4. Confirm the hybrid lesson-order principle, then write the actual lesson sequence.
5. Review the alef, ayin, and niqqud wording with the content assets in front of us.
6. Run the audio spike during the three-letter slice.

When a proposed answer is accepted, change its status here and update the architecture or product document that owns the decision.
