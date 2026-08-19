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
- identify which React Native skills transfer and which Kotlin/KMP areas need deliberate practice;
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

## Part 2 — React Native transfer inventory

Rate each from 0–5 using the mastery scale in [Assessment and progress](../assessment-and-progress.md), then give one example from Pocket Globe/Globe Game, the vocabulary app, or another project you implemented directly.

| Skill | Self-rating | Evidence/example |
|---|---:|---|
| Component composition and props | TBD | TBD |
| Local React state and effects | TBD | TBD |
| Navigation and route parameters | TBD | TBD |
| Shared state/context/providers | TBD | TBD |
| Pure product logic outside UI | TBD | TBD |
| Async work and error handling | TBD | TBD |
| Local persistence or database modeling | TBD | TBD |
| Native/platform integration | TBD | TBD |
| Unit and component testing | TBD | TBD |
| Release and store delivery | TBD | TBD |

## Part 3 — Kotlin/KMP baseline

Answer “know,” “rough idea,” or “new,” followed by any explanation you can give. Do not search.

1. What is the practical difference between `val` and `var`?
2. What does `String?` communicate?
3. What is a Kotlin `data class` useful for?
4. When might a `sealed interface` be safer than a string status?
5. What is the difference between a Kotlin package and a Gradle module?
6. What does `commonMain` restrict?
7. What does `@Composable` mean?
8. What does `remember` survive, and what does it not survive?
9. What problem does a ViewModel solve?
10. What does `suspend` mean?
11. How might `Flow<T>` differ from `List<T>`?
12. What is constructor injection?
13. What is a Room entity?
14. Why might a Room entity and UI model be different classes?
15. When would Android and iOS require separate implementations?

Incorrect answers are useful course-design evidence.

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
- [ ] RN and KMP baseline recorded.
- [ ] Product-action trace completed.
- [ ] State-lifetime reasoning reviewed.
- [ ] Coaching contract confirmed or revised.
- [ ] Independent explanation completed.
- [ ] Skill matrix baseline updated.
- [ ] Lesson 00.01 marked passed or revision notes recorded.

## Next lesson

Lesson 00.02 maps the actual repository, Gradle build, Kotlin packages, source sets, and Xcode host. The opening retrieval will revisit state ownership and the package/module distinction.
