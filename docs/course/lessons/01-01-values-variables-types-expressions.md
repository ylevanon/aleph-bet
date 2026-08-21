# Lesson 01.01 — Values, variables, types, and expressions

Status: Passed

Date passed: 2026-08-20

Module: 01 — Kotlin through the alphabet domain

## Product outcome

The shared starter now renders named Aleph Bet values instead of anonymous string literals:

- a screen title;
- the base-letter count;
- the number of introduced letters;
- a progress label derived with an `if` expression;
- a button label derived from observable UI state.

Every change is visible immediately through Desktop Hot Reload.

## Why this lesson is product-first

The original page delayed visible progress for a detached `commonTest` syntax lab. The revised lesson teaches the same foundational distinctions through the running application. Focused domain tests begin when the first actual domain rule exists rather than as ceremony around unrelated sample values.

## Learning outcomes

By the end, the learner can:

- distinguish `val` from `var`;
- explain that `val` is a read-only reference, not a deeply immutable object or compile-time constant;
- recognize inferred `String`, `Int`, and `Boolean` types;
- add an explicit type when it serves an instructional or API-boundary purpose;
- use string interpolation;
- assign the result of an `if` expression to a `val`;
- distinguish an ordinary local value from observable Compose state;
- explain why a derived `val` can produce different visible text across recompositions.

## Mental model

### Read-only and mutable references

```kotlin
val glyph = "א"
var isMastered = false
```

`glyph` cannot be assigned a different value. `isMastered` can be reassigned because its declaration uses `var`.

That does not make every object referenced by `val` deeply immutable. It only prevents assigning a different object to that declaration.

`const val` is a separate Kotlin feature for eligible compile-time constants. Ordinary local `val` values may be computed entirely at runtime.

### Type inference

```kotlin
val screenTitle: String = "Learn the Hebrew alphabet"
val baseLetterCount = 22
val isComplete = false
```

The explicit `String` demonstrates written type syntax. Kotlin infers `Int` and `Boolean` from the other initializers.

Local types should usually be inferred when they are obvious. Explicit types become more valuable at public boundaries or when they clarify intent.

### Expressions

Kotlin's `if` produces a value:

```kotlin
val progressText = if (introducedLetterCount == baseLetterCount) {
    "Alphabet complete"
} else {
    "$introducedLetterCount of $baseLetterCount letters introduced"
}
```

The selected branch's final expression becomes the value assigned to `progressText`. No mutable result variable or ternary operator is required.

## Actual guided implementation

Inside the shared `App()`, the learner introduced:

```kotlin
val screenTitle: String = "Learn the Hebrew alphabet"
val baseLetterCount = 22
val introducedLetterCount = 3
```

The visible progress text is derived from those facts rather than duplicating `3` and `22` inside the displayed sentence.

An initial attempt placed mutable values at file scope. The revision moved them into `App()` and changed them to local `val` declarations. This established two boundaries:

- stable display inputs do not need global mutable state;
- arbitrary Kotlin variables are not observable Compose state.

## Ordinary values and Compose state

The generated starter contains:

```kotlin
var showContent by remember { mutableStateOf(false) }
```

This is intentionally a `var`: clicking the button toggles it. The Compose state holder also causes code that reads it to recompose.

The button label is derived rather than stored independently:

```kotlin
val buttonLabel = if (showContent) {
    "Hide greeting"
} else {
    "Start learning"
}
```

On one composition, that local `val` receives one value. After `showContent` changes, Compose executes the relevant content again and a new local `val` is initialized from the new state. The declaration itself is never reassigned.

The React equivalent is a `const` derived from state during each component render. This analogy describes re-execution; it does not imply that Compose uses React's virtual DOM.

## Review evidence

The learner correctly predicted:

- reassigning a `val position` fails;
- a Hebrew glyph literal is inferred as `String`;
- `false` is inferred as `Boolean`;
- a learner-state value may need mutation while an authored glyph binding does not.

The learner then implemented and revised:

- local declarations and type inference;
- string interpolation;
- a progress `if` expression;
- a state-derived button label;
- the visible toggle behavior through Hot Reload.

The coach handled only final mechanical spacing and formatting cleanup after the learner requested it.

## Completion evidence

- [x] `val` and `var` behavior is predicted correctly.
- [x] Explicit and inferred local types are both used.
- [x] String interpolation renders values without duplicated numeric literals.
- [x] An `if` expression derives visible progress text.
- [x] A second `if` expression derives button text from Compose state.
- [x] Learner distinguishes reassignment from recomputation during recomposition.
- [x] Desktop Hot Reload verifies the visible result.
- [x] Application source remains local and unpushed while the configured remote is public.
- [x] Public progress record is updated.

## Next retrieval

Lesson 01.02 turns repeated value derivations into Kotlin functions, introduces named arguments, and models optional letter content without unsafe null assertions.

## References

- [Kotlin basic syntax](https://kotlinlang.org/docs/basic-syntax.html)
- [Kotlin basic types](https://kotlinlang.org/docs/basic-types.html)
- [Kotlin control flow](https://kotlinlang.org/docs/control-flow.html)
