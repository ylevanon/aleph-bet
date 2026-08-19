# Lesson 00.01 — Baseline and course map

Status: In progress

Module: 00 — Orientation and toolchain

Estimated focused time: 45–75 minutes

## Product outcome

No application code is written in this lesson. We establish the learner's baseline, agree on the coaching workflow, and trace the planned V1 from learner action to UI, domain behavior, and durable data.

The output is a calibrated course rather than a generic one.

## Learning outcomes

By the end, the learner can:

- explain the product-to-code sequence for Aleph Bet V1;
- distinguish a screen, ViewModel, domain rule, repository contract, and data source at a high level;
- classify representative state by owner and lifetime;
- explain how existing mobile and product knowledge transfers while Kotlin/KMP fluency is calibrated through real work;
- state how help, solutions, review, and lesson completion will work.

## Prerequisites

- Read the [course index](../README.md).
- Skim the [teaching method](../method.md).
- Do not study Kotlin answers in advance; this is a baseline, not an exam.

## Part 1 — Learning preferences

Answer candidly:

1. When Vadim's course worked best for you, what were you doing: copying, pausing and predicting, changing the example, or rebuilding later?
2. What usually causes you to abandon a technical course?
3. Do you prefer compiler-first experimentation or an explanation before touching code?
4. When blocked, how long do you want to struggle before receiving the first hint?
5. Are you comfortable narrating your reasoning even when uncertain?
6. Which matters more for this course: fastest store release, deepest Kotlin understanding, or an explicit balance?
7. How much uninterrupted time does a typical learning session realistically have?

These answers may change lesson size and hint timing without changing the product milestone.

## Part 2 — Transfer agreement

There is no React Native, Java, or Python fluency inventory.

The working assumptions are:

- the learner has already built and shipped mobile applications;
- React Native syntax may be rusty because it has not been typed regularly in the last year or two;
- recent development has leaned heavily into agentic engineering;
- occasional Java or Python algorithm practice does not need to become a course prerequisite;
- application concepts and unfamiliar APIs will be understood when they are explained in context.

The course therefore will not spend time proving recall of props, effects, navigation calls, or syntax in unrelated languages. React Native examples are translation aids, not an entrance exam.

What matters is demonstrated ability to reason about ownership and behavior, implement the Kotlin version, review generated suggestions, diagnose failures, and explain the result.

## Part 3 — Kotlin/KMP calibration policy

There is no up-front Kotlin terminology quiz either. Knowing or guessing definitions for `val`, `Flow`, `commonMain`, `remember`, or Room entities would be weak evidence of the ability to build the application.

Starting assumptions:

- unfamiliar Kotlin syntax is taught when the product first needs it;
- programming concepts transfer unless implementation evidence reveals a gap;
- KMP, Compose, Room, coroutine, and DI mental models are explained before their first production use;
- baseline mastery levels are recorded from prediction, implementation, debugging, and teach-back evidence in real Aleph Bet tasks.

No learner response is required for Parts 2 or 3. The first authentic tasks will supply better calibration.

## Part 4 — Trace one product action

Scenario:

> In Practice, the learner sees ב, selects the wrong name, sees feedback, exits the app, and returns tomorrow. The app should prioritize ב for review.

Describe what you think each area owns:

| Area | Question |
|---|---|
| Compose screen | What does it render and emit? |
| Practice ViewModel | What does it coordinate? |
| Session engine | What decision should be independent of UI? |
| Progress rules | What interpretation changes? |
| Repository | What contract does the caller need? |
| Room | Which durable facts should remain tomorrow? |
| Bundled curriculum | Which facts about ב never came from the learner? |

There is no requirement to use Kotlin vocabulary perfectly. We are looking for ownership instincts.

## Part 5 — State-lifetime classification

Classify each as Compose-local, ViewModel/session, saved checkpoint, Room, DataStore, or bundled content. Explain one ambiguous choice.

- glyph for bet;
- whether the audio button is currently pressed;
- current shuffled choices;
- selected wrong answer before feedback is dismissed;
- lifetime incorrect count for bet;
- next safe lesson step after process death;
- “play audio automatically” preference;
- reviewed explanation of bet/vet;
- last-practiced timestamp;
- whether a purely decorative card is expanded.

## Part 6 — Architecture map

Without looking at the answer file, complete this flow in your own words:

```text
learner action
    -> __________
    -> __________
    -> plain Kotlin rule or use case
    -> repository contract
    -> __________
    -> observable state
    -> __________
```

Then answer:

1. Why should Practice use Alphabet's domain facts but not `AlphabetScreen`?
2. Why is Progress shared but still a named owner rather than `utils`?
3. Why is Room not the source of truth for the letter glyph?

## Part 7 — Coaching contract

Confirm or revise these defaults:

- You write core application code.
- I maintain course docs, teach, review, run checks, and debug with you.
- I show a minimal example before your real implementation.
- You request hints by level before requesting a complete solution.
- Important lessons include an independent variation and teach-back.
- We do not mark a lesson passed until review findings are resolved.
- We commit small lesson changes privately and publish course/documentation changes publicly.

## Independent task

Write a short explanation for a React Native developer:

> Why will this course not simply give me finished Kotlin classes to type?

The answer should mention recognition versus recall, gradual responsibility, and the real Aleph Bet product.

## Exit ticket

1. Name one skill that transfers strongly from React Native.
2. Name one KMP constraint that React Native normally hides.
3. What determines whether state belongs in `remember`, a ViewModel, or Room?
4. What will you do before asking for a complete solution?
5. Which part of the planned course are you most uncertain about?

## Completion evidence

- [x] Learning-preference answers recorded.
- [x] Transfer assumptions and task-based KMP calibration policy recorded.
- [x] Product-action trace completed.
- [x] State-lifetime reasoning reviewed.
- [ ] Coaching contract confirmed or revised.
- [ ] Independent explanation completed.
- [ ] Skill matrix baseline updated.
- [ ] Lesson 00.01 marked passed or revision notes recorded.

## Next lesson

Lesson 00.02 maps the actual repository, Gradle build, Kotlin packages, source sets, and Xcode host. The opening retrieval will revisit state ownership and the package/module distinction.
