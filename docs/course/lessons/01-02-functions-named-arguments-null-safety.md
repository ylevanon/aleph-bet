# Lesson 01.02 — Functions, named arguments, and null safety

Status: Ready after Lesson 01.01

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 60–80 minutes

## Product outcome

Shared Kotlin helpers describe letter sounds and optional related forms without unsafe null assertions. Tests cover both present and absent optional content.

Use the Module 01 path convention established in Lesson 01.01.

## Learning outcomes

By the end, you can:

- write block-body and expression-body functions;
- declare parameter and return types and call functions with named arguments;
- use defaults when they express a stable API default;
- model meaningful absence with nullable types;
- use safe calls and the Elvis operator instead of `!!`;
- distinguish “no related item” from “an empty collection of items.”

## Prerequisites

- Lesson 01.01 passed.
- `LetterBasicsTest` runs in `commonTest`.

## Retrieval warm-up

1. Why are pointed/display Hebrew forms represented as `String`?
2. What is the result type of an `if` expression whose branches are strings?
3. Which repeated code in your first three summaries suggests a function boundary?

## Why the app needs this now

The authored alphabet includes both repeated and optional shapes:

- every letter can have zero or more sound descriptions;
- Bet has multiple visual/sound forms;
- final Kaf refers to a base letter, while Alef does not;
- missing content must not become the string `"null"` or a crash.

Functions let us name transformations. Nullability lets the type system distinguish a missing single relationship from a present value.

## Mental model

A function is a typed contract:

```text
parameters -> function body -> declared result
```

Nullability adds a branch to the input space:

```text
String     = a string must exist
String?    = a string may exist or may be null
List<T>    = a list always exists and may be empty
List<T>?   = the list itself may be absent (use only if that distinction matters)
```

For Aleph Bet:

- an optional single `baseLetterId` can be `LetterId?` later;
- sound descriptions should normally be `List<String>`, using an empty list when none are authored;
- `!!` discards the compiler's safety and is not acceptable in this module.

## React Native bridge

| TypeScript | Kotlin | Difference that matters |
|---|---|---|
| optional parameter `name?: string` | `name: String? = null` | Kotlin requires explicit handling before using it as non-null. |
| `value?.x ?? fallback` | `value?.x ?: fallback` | Kotlin's Elvis operator is `?:`; `null` is governed by the static type. |
| object parameter for call-site labels | named arguments | Named arguments label ordinary parameters, but public API compatibility still depends on parameter names. |
| non-null assertion `value!` | `value!!` | Both can hide bad assumptions; Kotlin throws if the value is null. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Parameter | Named input declared by a function. |
| Argument | Value supplied at a call site. |
| Return type | Type a function promises to produce. |
| Expression body | `fun name(...): Type = expression`. |
| Block body | Function body inside braces; explicit `return` is required for returned values. |
| Nullable type | A type marked `?` whose valid values include `null`. |
| Safe call | `?.`, which continues only when the receiver is non-null. |
| Elvis operator | `?:`, which supplies a value when the left side is null. |

## Predict before running

Predict all three results and explain the null path:

```kotlin
fun relatedLabel(baseId: String?, fallback: String = "no base form"): String =
    baseId?.let { "base: $it" } ?: fallback

val alef = relatedLabel(baseId = null)
val finalKaf = relatedLabel(baseId = "kaf")
val custom = relatedLabel(baseId = null, fallback = "standalone")
```

Then predict whether this compiles:

```kotlin
val unsafeLength: Int = null.length
```

Do not run until you can name the type error.

## Minimal demonstration

Create `LetterFunctionsTest.kt` in the Module 01 test-lab package. Type the `relatedLabel` function and assertions for its three calls.

Change the expression body into a block body and make it pass again. Then return to the version you find clearest and explain why.

Expected result: you have seen both body forms; no application model has been revealed.

## Guided lab

### Step 1 — Extract sound-count language

Turn Lesson 01.01's sound-count `when` into a function.

Requirements:

- input is a count;
- return type is explicit;
- negative counts are rejected or deliberately classified—choose and defend one behavior;
- branches produce the same three user-facing labels used previously.

Write the function before its tests, then add tests for `0`, `1`, `2`, and the negative behavior.

### Step 2 — Name a display helper

Write a function that combines a glyph, Latin name, and sound-count label. Call it once with positional arguments and once with named arguments.

Compare the call sites. Named arguments are most useful when adjacent parameters have similar types or unclear meaning; they are not required at every call.

### Step 3 — Model one optional relationship

Write a helper that accepts an optional base-letter ID and returns a readable relationship label. Cover:

- a normal letter with no base ID;
- `final_kaf` with base ID `kaf`.

Use either an explicit null check, a safe-call chain, or Elvis based on clarity. Do not use `!!`.

### Step 4 — Decide null versus empty

For each concept, choose `T`, `T?`, `List<T>`, or `List<T>?` and explain the product meaning:

1. Latin letter name.
2. Sound descriptions.
3. Base-letter ID for a final form.
4. A loader result before content has been loaded—note that this may later be modeled as a state rather than null.

The goal is not “avoid all null.” The goal is to make invalid ambiguity impossible or visible.

### Step 5 — Remove accidental nullable propagation

Write one version of a helper that returns `String?` unnecessarily, then inspect how every caller must handle it. Change the helper to return a non-null fallback `String` and compare the call sites.

Explain where the absence was resolved.

## Independent task

Implement and test a helper for an optional explanatory note:

```text
Input:
- letter Latin name
- optional note
- configurable fallback text

Behavior:
- a non-blank note produces "<name>: <note>"
- null or blank note produces the fallback
```

You choose the signature, body style, default value, and whether a small private helper improves clarity.

### Acceptance criteria

- Parameter and return types are explicit at the function boundary.
- At least one call uses named arguments because it improves readability.
- Null and blank are both tested.
- A present note is tested.
- No `!!` is used.
- Collections are not made nullable when an empty list carries the full meaning.
- The code remains in `commonTest` and has no platform/framework dependencies.

## Test and debugging plan

1. Run only `LetterFunctionsTest`.
2. Cover every null/non-null branch before refactoring.
3. For a compilation error, identify whether the receiver is nullable or the function return is nullable.
4. For a failed expectation, state whether the contract or implementation is wrong before editing.
5. Temporarily attempt a nullable operation without a safe call; explain the compiler's suggested alternatives, then remove it.

## Hint ladder

<details>
<summary>Hint 1 — Behavior</summary>

Resolve absent or blank notes at the boundary so callers receive a definite display string.

</details>

<details>
<summary>Hint 2 — Null and blank</summary>

Kotlin provides operations that can treat a nullable string as absent when it is null or blank. You may also express the branches with a clear `if`.

</details>

<details>
<summary>Hint 3 — Signature shape</summary>

Start with three parameters returning `String`. Give only the fallback parameter a default if the default is product-wide and unambiguous.

</details>

No complete independent-task solution is published before an attempt.

## Teach-back

Explain why `baseLetterId` may be nullable while `sounds` should normally be a non-null list, and how the chosen helper prevents null handling from spreading into UI callers.

## Exit ticket

1. What is the difference between an expression-body and block-body function?
2. When do named arguments improve a call site?
3. What valid value does `String?` add to `String`?
4. When should an absent collection be represented by `emptyList()`?
5. What guarantee do you throw away with `!!`?

## Review rubric

This lesson assesses function design, null-safety reasoning, branch tests, and Kotlin clarity. Passing requires the independent optional-note behavior and a defensible null-versus-empty explanation.

## Completion evidence

- [ ] Function/null predictions are recorded.
- [ ] Sound-count and relationship helpers pass their edge tests.
- [ ] Null-versus-empty choices are explained.
- [ ] Independent optional-note helper passes.
- [ ] No unsafe null assertion remains.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 01.03 replaces loose related values with types that encode valid letter states. Nullable relationships and non-null collections return in the content-mapping and Room modules.

## References

- [Kotlin functions](https://kotlinlang.org/docs/functions.html)
- [Kotlin null safety](https://kotlinlang.org/docs/null-safety.html)
