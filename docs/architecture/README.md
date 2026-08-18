# Aleph Bet architecture

Status: Working architecture decisions

Last updated: 2026-08-18

## Goal

Build a small, understandable Kotlin Multiplatform app that teaches the Hebrew alphabet offline on Android and iOS. The first architecture should support the alphabet release without pretending we already need the infrastructure for a full language platform.

## Read in this order

1. [Project structure and platform boundaries](project-structure.md)
2. [Feature, model, and data boundaries](features-and-data.md)
3. [Presentation and dependency injection](presentation-and-di.md)
4. [Testing, delivery, and learning sequence](testing-and-delivery.md)
5. [Architecture answers and decision queue](answers.md)

The product behavior that drives these decisions is documented in the [Alphabet V1 roadmap](../product/alphabet-v1-roadmap.md).

## System at a glance

```text
Compose screen
    renders UiState
    sends actions
         |
         v
ViewModel
    coordinates a screen or session
         |
         v
Plain Kotlin learning logic and use cases
         |
         v
Repository interfaces
      /       \
     v         v
Bundled       Room
curriculum    learner state
```

Android and iOS implementations sit behind small common interfaces when the operating systems genuinely differ, most notably for audio and database construction.

## Current decisions

- Shared UI with Compose Multiplatform.
- One `composeApp` Gradle module plus the native `iosApp` wrapper.
- Packages organized primarily by product feature.
- Plain Kotlin lesson and progress rules.
- Unidirectional screen state through ViewModels.
- Koin Annotations with the Koin Compiler Plugin.
- Bundled JSON and audio as curriculum source of truth.
- Room for durable learner progress.
- DataStore for small preferences.
- A three-letter vertical slice before scaling the curriculum.

## Core dependency rule

```text
presentation -> domain <- data
```

- Presentation depends on domain concepts and operations.
- Data implements domain repository contracts.
- Domain code does not depend on Compose, Room, Android, or iOS.
- Platform code implements only the behavior that cannot be shared safely.

These are working decisions. The vertical slice is allowed to prove one wrong, but a change should be recorded with the reason and consequence.

Open questions, proposed answers, and the state-lifetime exercise answer key are maintained in [Architecture answers and decision queue](answers.md).
