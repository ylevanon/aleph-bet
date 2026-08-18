# Testing, delivery, and learning sequence

Status: Working architecture decision

Last updated: 2026-08-18

## Testing strategy

### `commonTest`

Most high-value behavior tests belong here:

- all bundled letters map into valid domain models;
- lesson definitions reference existing letters and audio;
- the session engine advances after correct and incorrect answers;
- answer choices contain the correct answer and no duplicate concepts;
- final forms resolve to their base letters;
- progress rules produce the agreed learning stages;
- practice selection follows the review policy;
- a saved checkpoint restores a valid session.

### Database tests

Test DAO behavior, repository mappings, transactions, and every migration. Migration tests protect learner progress across app updates.

### UI tests

Use focused Compose tests where semantics are the clearest proof:

- right-to-left ordering;
- accessibility labels;
- enabled and disabled states;
- answer feedback;
- navigation entry points;
- large-text behavior.

### Platform tests

Keep a small set of Android and iOS integration tests for:

- database creation;
- audio playback and interruption handling;
- bundled-resource availability;
- app lifecycle boundaries.

## Three-letter vertical slice

The first implementation slice should contain:

- three bundled letters;
- one authored lesson;
- letter-name and sound audio;
- at least two exercise types;
- lesson progress and resume behavior;
- a small alphabet explorer;
- tests for the session and progress transitions.

This slice validates the full architecture. It is not a prototype that bypasses persistence, audio, or tests and therefore teaches nothing about the final system.

## Build and learning sequence

1. Create the KMP/Compose shell and run one shared screen on Android and iOS.
2. Render one hard-coded letter to learn Composables, parameters, previews, and local state.
3. Load three letters from bundled JSON and map DTOs into domain models.
4. Introduce `AlphabetRepository` when presentation needs content without knowing its source.
5. Build and test a plain Kotlin lesson session before connecting it to Compose.
6. Expose the session through immutable UI state, actions, and a ViewModel.
7. Add Koin Annotations for application assembly while tests keep using constructors.
8. Add Room when the slice first needs durable progress and resume behavior.
9. Add the common audio contract and Android/iOS implementations.
10. Verify process recreation, offline behavior, RTL, and accessibility.
11. Expand to all 22 letters and five final forms only after the slice works end to end.

At every step, be able to draw the data flow, identify the owner and lifetime of each state value, and explain why a new abstraction exists.

## Definition of architecture-ready

The vertical slice is architecturally ready to scale when:

- Android and iOS run the same lesson behavior;
- content is loaded from real bundled resources;
- progress survives the promised interruptions;
- lesson rules have deterministic common tests;
- Room entities do not leak into UI state;
- platform audio types do not leak into common domain logic;
- adding a fourth letter is a content operation, not an architecture rewrite.

## Explicitly deferred

- multiple Gradle feature modules;
- a generic framework for every future content type;
- backend and sync architecture;
- analytics architecture;
- purchase SDK and entitlement implementation;
- automatic pronunciation analysis;
- abstractions with no current consumer.

## Open questions for the vertical slice

Proposed answers and their current decision status are tracked in [Architecture answers and decision queue](answers.md).

1. How much of an interrupted lesson must survive process death?
2. Do we retain individual attempts or only progress aggregates?
3. Which audio library provides reliable shared behavior?
4. Does the session engine need a general step model or a small sealed set of explicit step types?
5. What evidence moves a letter from `PRACTICING` to `SOLID`?
6. At what measured cost would multiple Gradle modules become worthwhile?
