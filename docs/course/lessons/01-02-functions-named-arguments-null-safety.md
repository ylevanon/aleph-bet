# Lesson 01.02 — Functions, named arguments, and null safety

Status: Passed

Date passed: 2026-08-20

Module: 01 — Kotlin through the alphabet domain

## Product outcome

The shared starter now uses two plain Kotlin formatting functions:

- alphabet progress is calculated outside Compose;
- letter labels support optional sound hints without rendering `null` or using `!!`.

The UI visibly renders Alef without a sound hint and Bet with `b or v`.

## Learning outcomes

By the end, the learner can:

- identify a function's inputs and output before writing it;
- declare typed parameters and a return type;
- distinguish a block body from an expression body;
- call a function with named arguments;
- use a default parameter;
- distinguish `String` from `String?`;
- handle null and empty strings without a forced non-null assertion;
- explain why deterministic formatting logic does not belong to Compose.

## Functions as boundaries

A function is a typed contract:

```text
inputs -> named behavior -> output
```

The progress formatter receives two `Int` values and returns a `String`. It contains no Compose calls and no mutable state. Given the same values repeatedly, it produces the same result.

```kotlin
private fun formatAlphabetProgress(
    introducedLetterCount: Int,
    baseLetterCount: Int,
): String {
    return if (introducedLetterCount == baseLetterCount) {
        "Alphabet complete"
    } else {
        "$introducedLetterCount of $baseLetterCount letters introduced"
    }
}
```

A block-body function uses braces and an explicit `return`. An expression-bodied function replaces the outer braces with `=`:

```kotlin
private fun formatAlphabetProgress(
    introducedLetterCount: Int,
    baseLetterCount: Int,
): String =
    if (introducedLetterCount == baseLetterCount) {
        "Alphabet complete"
    } else {
        "$introducedLetterCount of $baseLetterCount letters introduced"
    }
```

Both forms are valid. Short transformations often read well as expression bodies; blocks remain useful when a function needs several steps.

## Named arguments

The call site uses labels:

```kotlin
val progressText = formatAlphabetProgress(
    introducedLetterCount = introducedLetterCount,
    baseLetterCount = baseLetterCount,
)
```

Named arguments matter here because both arguments are `Int`. A reversed positional call would compile but express the wrong relationship. The labels expose the mapping at the call site.

Named arguments are not mandatory everywhere. Use them when they remove ambiguity or make a call scan more clearly.

## Nullability

Kotlin types are non-null by default:

```kotlin
val hint: String = null // does not compile
```

A question mark adds `null` to the type's valid values:

```kotlin
val hint: String? = null
```

The letter-label formatter accepts an optional sound hint with a default:

```kotlin
private fun formatLetterLabel(
    glyph: String,
    latinName: String,
    soundHint: String? = null,
): String {
    if (soundHint.isNullOrEmpty()) {
        return "$glyph — $latinName"
    }
    return "$glyph — $latinName ($soundHint)"
}
```

The default lets Alef omit the argument. Bet supplies a non-null value with a named argument.

`isNullOrEmpty()` is defined for nullable strings and returns true for both `null` and `""`. It does not treat whitespace-only text as empty; `isNullOrBlank()` would.

No `!!` is needed. A forced assertion would turn missing content into a runtime crash instead of handling it in the function's contract.

## Defaults are separate from nullability

This declaration is invalid:

```kotlin
soundHint: String = null
```

`String` cannot contain `null`.

If the default is removed, callers must provide a non-null string. If a non-null default is provided, callers may still omit the argument:

```kotlin
soundHint: String = "No sound hint"
```

Whether absence and a fallback string mean the same product state is a modeling decision, not merely syntax.

## Actual implementation evidence

The learner:

- identified two `Int` inputs and a `String` output before extraction;
- explained that no mutable declarations were required;
- extracted progress formatting from the Composable;
- used named arguments after recognizing the risk of adjacent positional `Int` values;
- designed a nullable sound-hint parameter with a null default;
- independently found `isNullOrEmpty()`;
- rendered Alef without a hint and Bet with `b or v`;
- avoided `!!`;
- asked how the formatter would look without `return`, leading to the expression-body comparison.

The coach corrected only mechanical visibility, punctuation, spacing, and call formatting after the learner requested that level of review handling.

## Review policy established

- The learner revises mistakes in the concept currently being practiced.
- The coach may repair mechanical visibility, spacing, formatting, or small typos and then report the change.
- Architecture or behavior changes are discussed before implementation.

## Completion evidence

- [x] Function inputs and output are predicted correctly.
- [x] Plain formatting logic is extracted from Compose.
- [x] Named arguments clarify adjacent same-typed parameters.
- [x] A nullable parameter and default are implemented.
- [x] Null and empty strings produce a safe label.
- [x] Present optional content renders visibly.
- [x] No unsafe null assertion is used.
- [x] Block and expression function bodies are distinguished.
- [x] Desktop Kotlin compilation succeeds.
- [x] Public progress record is updated.

## Next retrieval

Lesson 01.03 replaces loose glyph, name, and hint parameters with Kotlin types that keep related letter facts together and represent valid variants explicitly.

## References

- [Kotlin functions](https://kotlinlang.org/docs/functions.html)
- [Kotlin null safety](https://kotlinlang.org/docs/null-safety.html)
