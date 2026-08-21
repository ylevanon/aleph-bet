# Lesson 01.03 — Data classes, value classes, enums, and sealed types

Status: Passed on 2026-08-20

Module: 01 — Kotlin through the alphabet domain

## Product outcome

The running shared application now represents Aleph and Bet as `Letter` values rather than unrelated glyph, name, and sound arguments. Letter identity is protected by a `LetterId` value class.

Enums and sealed types were learned through product-state modeling, but were deliberately not made part of the accepted V1 domain merely to demonstrate syntax. They return when Progress and Practice create real requirements.

## Learning outcomes

By the end of this lesson, the learner can:

- explain why a data class fits an immutable domain value;
- use generated data-class equality and `copy()` without mutating the original;
- distinguish a read-only property from a mutable collection stored in that property;
- use a value class to prevent unrelated strings from being interchangeable;
- choose an enum for fixed, same-shaped singleton values;
- choose a sealed family when cases require different data;
- distinguish a singleton `object` from separately constructed class instances;
- avoid introducing domain types before the product state they model exists.

## Starting problem

The previous formatter accepted separate values:

```kotlin
formatLetterLabel(
    glyph = "ב",
    latinName = "Bet",
    soundHint = "b or v",
)
```

This lets a caller accidentally combine Bet's glyph with Aleph's name. The parameters are individually valid strings, but the combination is invalid.

The product needs a value that keeps one letter's facts together.

## Part 1 — Model a letter with a data class

The first domain model was created in `alphabet/domain`:

```kotlin
data class Letter(
    val id: LetterId,
    val order: Int,
    val glyph: String,
    val latinName: String,
    val sounds: List<String> = emptyList(),
)
```

Why a data class fits:

- its properties describe the value;
- two letters with equal properties compare structurally with `==`;
- `copy()` can derive a changed value without mutating the original;
- `toString()` is useful during tests and debugging.

### `val` does not make every nested object immutable

This draft was corrected:

```kotlin
val sounds: List<String> = mutableListOf()
```

`val` prevents replacing the `sounds` reference. It does not promise that a mutable collection stored behind that reference can never change. The model instead uses:

```kotlin
val sounds: List<String> = emptyList()
```

Authored sound values are supplied with read-only lists such as `listOf("b", "v")`.

## Part 2 — Use the model at the UI boundary

The shared application constructs Aleph and Bet as complete values:

```kotlin
val aleph = Letter(
    id = LetterId("aleph"),
    order = 1,
    glyph = "א",
    latinName = "Aleph",
)

val bet = Letter(
    id = LetterId("bet"),
    order = 2,
    glyph = "ב",
    latinName = "Bet",
    sounds = listOf("b", "v"),
)
```

The formatter now accepts one coherent value:

```kotlin
private fun formatLetterLabel(letter: Letter): String
```

This is more than shorter call-site syntax. It prevents glyph, name, and sound facts from different letters being mixed accidentally.

`joinToString(" or ")` converts Bet's sound list into the display phrase `b or v`. Full collection practice follows in Lesson 01.04.

## Part 3 — Understand generated data-class behavior

Given:

```kotlin
val anotherBet = bet.copy(sounds = listOf("v"))
```

The learner correctly predicted:

- `id`, `order`, `glyph`, and `latinName` remain the same;
- only `sounds` changes in `anotherBet`;
- the original `bet` does not change.

This immutable-update pattern becomes important when Compose and ViewModels expose UI state.

## Part 4 — Protect identity with a value class

If letter IDs and lesson IDs are both plain strings, Kotlin allows them to be mixed up. A value class gives one primitive a distinct domain identity:

```kotlin
@JvmInline
value class LetterId(val value: String)
```

Now a function expecting `LetterId` cannot accidentally receive an arbitrary `String` or a future `LessonId`.

### Data class versus value class

| Type | Use here |
|---|---|
| `data class Letter` | Groups several related properties that define a complete value. |
| `value class LetterId` | Gives one underlying `String` a distinct compile-time identity. |

Validation such as rejecting blank IDs will be introduced with construction-boundary tests rather than added without a failing example.

## Part 5 — Enum versus sealed family

An enum is a good fit when all valid options are predefined singleton values with the same shape:

```kotlin
enum class MasteryLevel {
    NEW,
    LEARNING,
    MASTERED,
}
```

Whether those exact mastery categories belong in V1 remains a Progress-domain decision. The declaration is a syntax example, not yet an accepted product requirement.

A sealed family is useful when the valid cases form a closed set but require different information. The product discussion used temporary answer feedback:

- unanswered carries no values;
- correct may carry the selected letter if the UI highlights it;
- incorrect may carry both the selected and correct letter IDs.

That could eventually have this shape:

```kotlin
sealed interface AnswerFeedback {
    data object Unanswered : AnswerFeedback

    data class Correct(
        val letterId: LetterId,
    ) : AnswerFeedback

    data class Incorrect(
        val selectedLetterId: LetterId,
        val correctLetterId: LetterId,
    ) : AnswerFeedback
}
```

This is intentionally a discussion model. The final type should be designed alongside `PracticeUiState`, because the current question may already own some of this information.

### Object versus class

- An `object` is one shared singleton. It cannot receive different constructor values for different occurrences.
- A class creates separate instances. Each instance can carry its own constructor values.
- A `data object` provides value-oriented generated behavior for a stateless singleton in a sealed family.
- A `data class` provides value-oriented generated behavior for constructed instances with data.

## Architectural placement

- `Letter` and `LetterId` belong in `alphabet/domain` because they describe bundled alphabet content independently of UI and storage.
- Answer feedback should eventually belong to Practice presentation/session ownership, not Alphabet merely because it references a letter ID.
- Mastery terminology should eventually belong to Progress if the scheduling rules prove that V1 needs discrete mastery levels.

Referencing a type from another domain does not transfer ownership of the behavior to that type's package.

## Evidence and review

The learner:

- chose non-null letter facts and an empty list for absent sounds;
- rejected putting an audio player service inside `Letter`;
- created the `Letter` data class and `LetterId` value class;
- instantiated Aleph and Bet in the running shared application;
- explained what changes and what remains unchanged during `copy()`;
- identified that answer-feedback cases may require different payloads;
- challenged an underspecified `Correct` singleton and correctly concluded that it becomes a class if the UI needs a letter ID;
- distinguished current screen feedback from the durable attempt later stored in Room.

Desktop Kotlin compilation passed after the model and call-site changes.

## Exit ticket answers

1. A data class groups the related properties of one letter and gives them structural equality.
2. A value class prevents a letter ID from being confused with unrelated strings while normally retaining lightweight runtime representation.
3. `copy()` returns a new value; it does not mutate the original data-class instance.
4. An enum fits fixed same-shaped singleton choices; a sealed family fits a fixed set of cases with differing required data.
5. A stateless case can be an object. A case that must carry per-occurrence values must be a class.

## Completion record

- [x] `Letter` is represented by an immutable data class.
- [x] Missing sounds use an empty read-only list rather than null.
- [x] The formatter accepts a coherent `Letter` value.
- [x] Aleph and Bet render from domain values.
- [x] `LetterId` prevents raw-string identity mixing.
- [x] Data-class copy behavior is explained correctly.
- [x] Enum and sealed-family selection is explained in product terms.
- [x] Object versus class payload behavior is explained.
- [x] Desktop Kotlin compilation succeeds.
- [x] Product types without current requirements remain provisional.

## Next lesson

Lesson 01.04 replaces the two local letter variables with an authored collection and uses Kotlin collection operations to order, find, filter, and transform alphabet content.
