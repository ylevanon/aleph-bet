# Alphabet V1 roadmap

Status: Approved alphabet-only V1 scope; listed product rules remain open until their owning lesson
Last updated: 2026-08-22

## Release goal

The first store release should be a small learning product, not only an alphabet reference.

Its promise is:

> Learn to recognize all printed Hebrew block letters, recall their names and common Modern Hebrew sounds, understand the five final forms, and follow Hebrew's right-to-left reading direction.

This scope deliberately excludes niqqud instruction, vocabulary, handwriting, pronunciation recording, accounts, cloud sync, streaks, and payments.

The [course completion matrix](../course/v1-completion-matrix.md) is the authoritative mapping from this product scope to lessons, work ownership, and verification gates.

The repository already contains the content foundation for this release:

- 22 base letters;
- five final forms;
- letter names and pointed names;
- common sounds and important sound variants;
- audio for letter names and sound samples;
- a Hebrew font and visual design tokens.

## From product outcome to implementation

The implementation should follow the product reasoning in this order:

```text
Learning outcome
    -> learner activities
    -> screens and interactions
    -> UI state and ViewModels
    -> domain concepts
    -> bundled content and persistent data
```

Screens inform the state and data model, but they should not be the original source of the product model. Starting with database tables risks letting storage dictate the learning experience; starting with isolated screens risks producing an attractive reference app that does not create recall.

## Minimum feature set

### 1. Guided lessons

The alphabet should be divided into approximately seven short lessons. Each lesson introduces a small group of letter forms and immediately asks the learner to retrieve what they saw or heard.

A working sequence is:

| Lesson | New content |
|---|---|
| 1 | Right-to-left orientation, א ב ג |
| 2 | ד ה ו |
| 3 | ז ח ט |
| 4 | י כ ך ל |
| 5 | מ ם נ ן ס |
| 6 | ע פ ף צ ץ |
| 7 | ק ר ש ת |

This sequence is provisional. Before implementation, decide whether the curriculum should favor conventional alphabet order, a confusion-aware sequence, or a hybrid. Final forms should remain visibly attached to their base letter even when counted as separate forms in progress.

Each lesson can reuse one loop:

1. Introduce a letter.
2. Show and play its name.
3. Explain and play its common sound or sounds.
4. Explain an important variant or final form when relevant.
5. Ask a quick recognition question.
6. Introduce the next letter.
7. End with a short mixed checkpoint.

A session should take roughly two to four minutes, but progress should be saved after every meaningful step so a learner can stop sooner.

### 2. Practice and retrieval

Practice is required for the app to teach rather than merely present information.

Three exercise types are sufficient for the first release:

- See a glyph, then choose its name or common sound.
- Hear a letter name or sound, then choose its glyph.
- See a final form, then choose its base letter.

Text entry should be deferred. Requiring a Hebrew keyboard or accepting multiple transliteration spellings adds considerable input complexity without materially improving the first learning promise.

### 3. Alphabet explorer

The learner can browse all 22 letters and five final forms, whether or not they have reached them in lessons. Lessons provide sequence; the explorer provides agency.

The learner can:

- see introduced and not-yet-introduced forms;
- open a letter detail view;
- replay letter-name and sound audio;
- inspect sound variants;
- see the relationship between a base letter and final form;
- review the letter's current learning status.

Reference content should not be paywalled or locked behind lesson completion.

### 4. Lightweight progress

The app should report progress in plain, defensible language:

- forms introduced;
- forms practiced;
- forms needing practice;
- completed lessons;
- current resumable lesson.

Avoid a precise "mastery percentage" in this release. Seeing an item once or answering it correctly twice in one session does not establish durable recall.

Working learning states could be:

- `NOT_INTRODUCED`
- `INTRODUCED`
- `PRACTICING`
- `SOLID`

The exact transition rules are a product decision that must be defined before implementing progress calculations.

## Screen map

### Start

The first-run screen explains only that the app teaches printed Modern Hebrew, Hebrew reads right to left, and audio is central. It then offers **Start learning**.

Do not add an account, goals survey, placement test, notification prompt, or payment screen.

### Learn/Home

The default screen shows:

- the current lesson or next lesson;
- overall alphabet progress;
- a continue action;
- a practice action when learned material exists;
- an entry to the alphabet explorer.

A permanent multi-tab navigation system is not yet required. The product may be clearer as one compact home that leads to Learn, Practice, and Alphabet.

### Lesson session

One navigation destination should host multiple lesson states:

- letter introduction;
- audio interaction;
- multiple-choice exercise;
- answer feedback;
- lesson recap.

These are states of a session, not five different routes. Navigation routes should represent destinations, not every card or animation shown inside a lesson.

### Alphabet explorer

The explorer presents all forms as a grid or compact list. A cell communicates the glyph, Latin name, and learning status without overwhelming the learner.

### Letter detail

The detail view contains:

- a large glyph;
- pointed Hebrew name;
- Latin name;
- common sound or sounds;
- audio controls;
- alternate forms and final-form relationship;
- concise beginner explanation;
- learning status.

This may be a screen or a sheet. The choice should be based on how much explanatory content and navigation depth the final design needs.

### Session completion

The final lesson state shows what was introduced, what needs another look, and one clear next action: practice again, return home, or continue.

### Settings/About

A minimal release still needs a place for audio preferences, reset progress, attribution, privacy information, and app version. This is release infrastructure rather than a core learning feature.

## ViewModel responsibilities

Compose screens should render immutable UI state and emit actions. ViewModels accept actions, coordinate domain operations, and expose observable state.

```text
Composable screen
    renders UiState
    emits UiAction

ViewModel
    accepts UiAction
    calls repositories and use cases
    exposes StateFlow<UiState>
```

### HomeViewModel

Provides the resumable lesson, next lesson, alphabet progress, and number of items needing practice. It handles navigation intents such as continue, practice, and explore, while the navigation host performs the actual navigation.

### LessonViewModel

Provides the current lesson, step, content card or exercise, answer choices, feedback, audio presentation state, and completion summary.

It handles play audio, select answer, continue, exit, and resume. Queue construction, answer evaluation, and progress rules should live in a session engine or use cases rather than growing into a large collection of unrelated conditions inside the ViewModel.

### AlphabetViewModel

Provides the complete alphabet joined with learner progress. It may later support grouping or filtering, but those controls are not release requirements.

### LetterDetailViewModel

Provides one letter, related forms and sounds, and its learner-progress summary.

### PracticeViewModel

Uses the same exercise/session engine as lessons but receives a different queue:

- a lesson queue follows authored curriculum steps;
- a practice queue selects from introduced concepts using learner progress.

## Core domain concepts

### Bundled curriculum content

Authored, immutable content ships with the app:

- `Letter`
- `LetterForm`
- `LetterSound`
- `Lesson`
- `LessonStep`
- `ExerciseDefinition`
- `AudioAsset`

The existing `alphabet.json` supplies much of the letter content. A separate authored curriculum definition is still needed because alphabetical content order is not the same thing as a lesson plan.

Bundled JSON and Compose resources should remain the source of truth for curriculum. They are versioned with the app, identical for every learner, and reviewed as content rather than mutated on the device.

### Learner-generated state

Room stores what this learner has done:

- current and completed lesson progress;
- per-concept learning progress;
- practice attempts or aggregate performance;
- durable resume information.

Preferences that do not need relational queries may live in DataStore instead of Room.

```text
Bundled resources                 Room / DataStore
-------------------------------   -------------------------------
Which letters and lessons exist   What this learner has done
Names, sounds, and explanations   Lesson completion and resume
Audio resource paths              Per-letter learning progress
Authored lesson sequence          Practice history or aggregates
```

Room should not become a duplicate content-management system for immutable JSON.

## Tentative persistence model

Table and field names are provisional. They exist to expose the information the product needs, not to lock implementation prematurely.

### `lesson_progress`

- `lesson_id`
- `status`
- `current_step_index`
- `started_at`
- `completed_at`
- `updated_at`

### `concept_progress`

- `concept_id`
- `concept_type`
- `introduced_at`
- `last_practiced_at`
- `correct_count`
- `incorrect_count`
- `consecutive_correct`
- `learning_stage`
- `next_practice_at`

Although the first release contains letters, `concept_type` creates a modest extension point for `LETTER`, `NIQQUD`, and `WORD`. Do not build a more abstract content framework until those later modules create a concrete need.

### `practice_attempt` (decision pending)

- `id`
- `concept_id`
- `exercise_type`
- `was_correct`
- `answered_at`
- `response_time_ms`

Keeping individual attempts makes review behavior easier to change, test, and debug. Omitting them produces a smaller schema if aggregate progress is sufficient. Local data volume would be small, so the decision should be based on learning and debugging needs rather than storage size.

## State lifetimes

Not every visible value belongs in Room.

| Lifetime | Examples |
|---|---|
| Composition-local state | animation progress, pressed state, expanded controls |
| ViewModel/session state | current exercise, shuffled choices, selected answer, feedback |
| Saved session state | enough information to restore an interrupted lesson safely |
| Room | lesson progress, last-practiced time, durable performance |
| Bundled content | glyph, letter explanation, sound definitions, lesson authoring |

The session should persist progress at meaningful boundaries. It does not need to serialize every transient animation or pressed state.

## Explicitly deferred

- Niqqud curriculum
- Vocabulary
- Recording and waveform comparison
- Automated pronunciation feedback
- Streaks and notifications
- Accounts and cloud sync
- Achievements and social features
- Handwriting and tracing
- General subscriptions, paywalls, and one-time-purchase system

Words may later become a one-time purchase. The alphabet release needs only a clean module or entitlement boundary in the product model; it does not need payment infrastructure.

Competition exception: while the RevenueCat Shipaton goal remains active, the release track includes one bounded qualifying RevenueCat-powered purchase or ad path. That path is a conditional release overlay, not a general monetization feature or payments curriculum. It must not paywall alphabet reference content or expand the core V1 scope. If the competition goal is removed, this exception disappears.

## Delivery roadmap

### Milestone 1: product definition

- Finalize the learning promise.
- Choose the lesson sequence.
- Define progress-state transition rules.
- Finalize the three exercise types.
- Storyboard the minimum screens.

### Milestone 2: three-letter vertical slice

- One short lesson.
- Letter-name and sound audio.
- Two exercise types.
- Resume behavior.
- Durable progress.
- A small alphabet explorer.

This milestone validates the complete user and data flow; it does not optimize for content volume.

### Milestone 3: reusable learning engine

- Authored lesson steps.
- Practice queue construction.
- Answer evaluation.
- Progress updates.
- Incorrect-answer behavior.
- Session completion.
- Process recreation and offline testing.

### Milestone 4: complete alphabet

- All 22 base letters and five final forms.
- Important sound variants.
- Complete lesson sequence.
- Confusable-letter review.
- Accessibility, large-text, and RTL verification.

### Milestone 5: store readiness

- First-run orientation.
- Settings and reset.
- Privacy and attribution.
- Audio interruption handling.
- App icon and store screenshots.
- Release and device testing.
- Conditional Shipaton qualification path and submission evidence while that goal is active.

## Open decisions

1. Alphabetical, confusion-aware, or hybrid lesson order?
2. What observable behavior moves a letter from `PRACTICING` to `SOLID`?
3. Should individual practice attempts be retained, or only aggregates?
4. Does letter detail need a full route or a sheet?
5. How should alef and ayin sounds be described without teaching misleading isolated consonant sounds?
6. How much awareness of niqqud is necessary to explain existing pointed sound samples without starting the niqqud curriculum?

## Architecture exercise

Work through these before checking the [answer key](../architecture/answers.md#state-lifetime-exercise-answer-key).

Classify each value as composition-local state, ViewModel/session state, Room-persisted state, or bundled curriculum content:

- the current letter's glyph;
- whether the audio button is pressed;
- the shuffled answer choices;
- the learner's lifetime correct count for `bet`;
- the explanation of bet/vet;
- the current step in an unfinished lesson;
- whether the answer feedback card is expanded;
- the timestamp when the learner last practiced `bet`.

The shuffled choices and unfinished lesson step are intentionally subtle. Their correct lifetime depends on the desired behavior during configuration changes, process recreation, and deliberate session exit.
