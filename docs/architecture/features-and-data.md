# Feature, model, and data boundaries

Status: Working architecture decision

Last updated: 2026-08-18

## Alphabet

Owns factual letter content and the browsable reference experience.

Core domain concepts:

- `Letter`
- `LetterForm`
- `LetterSound`
- `AlphabetRepository`

The data layer loads and maps bundled `alphabet.json`. Presentation owns the alphabet explorer and letter detail screen.

Alphabet does not decide lesson order or learner mastery. Learning and Progress own those meanings.

## Learning

Owns authored lessons and the reusable session machinery that presents content, asks questions, evaluates answers, and advances the learner.

Core domain concepts:

- `Lesson`
- `LessonStep`
- `Exercise`
- `LessonSession`
- `LessonSessionEngine`

The engine should be plain Kotlin. Given a session and an action, it produces the next session state. It should not require Compose, Room, Koin, Android, or iOS.

## Practice

Owns selection of previously introduced concepts for review.

Practice reuses Learning's exercise and session machinery:

- Guided learning follows an authored lesson queue.
- Practice constructs a queue from learner progress and review rules.

Practice should not become a second lesson engine.

## Progress

Owns the meaning and storage of learner progress.

Core domain concepts:

- `ConceptId`
- `ConceptType`
- `LearningStage`
- `ConceptProgress`
- `LessonProgress`
- `ProgressRepository`

Its data layer owns Room entities, DAOs, mappings, migrations, and the Room-backed repository.

Progress rules separate facts from product interpretations. `correctCount = 4` is recorded evidence; deciding that four answers make a letter `SOLID` is a domain rule that must be defined and tested.

## Audio

Owns the common playback contract and observable playback state. Common code understands operations such as play and stop, not AVFoundation or Android media-player types.

## Settings

Owns small preferences such as audio behavior and completed onboarding. These fit DataStore rather than Room unless relational queries become necessary.

## Data ownership

| Information | Source of truth | Reason |
|---|---|---|
| Letter glyph, name, sounds, explanation | Bundled JSON/resources | Immutable authored curriculum |
| Lesson sequence and steps | Bundled JSON/resources | Editorial content versioned with the app |
| Reviewed audio | Compose resources | Offline media shipped with the app |
| Lesson completion | Room | Durable relational learner state |
| Per-letter progress | Room | Shared by learning, practice, and progress UI |
| Practice attempts, if retained | Room | Durable review evidence and debugging history |
| Audio preference and onboarding flag | DataStore | Small key-value preferences |
| Selected answer and animation state | ViewModel or Compose state | Temporary interaction state |
| Recoverable lesson checkpoint | Saved state and/or Room | Must match the product's interruption promise |

Do not duplicate the bundled curriculum in Room. A repository or use case joins immutable content with learner progress when a screen needs both.

## Model boundaries

The same letter may have several representations because each boundary changes for a different reason.

### Content DTO

Matches the JSON schema and serialization requirements. It can contain nullable or version-specific fields.

### Domain model

Represents a valid letter, lesson, exercise, or progress concept in learning logic. It should make invalid states difficult to construct.

### Room entity

Matches database storage, keys, and indexing. It changes when persistence needs change.

### UI state

Contains exactly what one screen needs to render. It may combine content and progress, but it should not expose Room entities to Compose.

Explicit mappings prevent a JSON, database, or layout change from leaking through the whole app.

## Initial Room responsibilities

The first schema is expected to center on:

- lesson progress and resume position;
- per-concept learning progress;
- optional individual practice attempts.

Exact tables and columns remain product decisions until progress-state transitions and resume behavior are defined in the vertical slice.
