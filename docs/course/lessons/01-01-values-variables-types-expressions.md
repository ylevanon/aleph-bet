# Lesson 01.01 — Values, variables, types, and expressions

Status: Ready after Module 00

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 60–75 minutes

## Product outcome

A shared Kotlin test lab represents basic facts about Alef, Bet, and Gimel and derives readable summaries. The code is deliberately small, but it is typed and changed by you rather than copied as a finished class.

## Path convention for Module 01

Use the authoritative map from Lesson 00.02:

- `<shared-module>` means the generated shared Kotlin module;
- `<package-root>` means the actual root package, expected to be `com.alephbet`;
- learning labs live under `<shared-module>/src/commonTest/kotlin/<package-root>/learning/kotlin/`;
- production domain declarations introduced at the checkpoint live under `<shared-module>/src/commonMain/kotlin/<package-root>/alphabet/domain/`.

Do not literally create folders containing angle brackets.

## Learning outcomes

By the end, you can:

- declare read-only and mutable local variables with `val` and `var`;
- use type inference while adding explicit types where they clarify a boundary;
- explain why Hebrew glyph content should be modeled as `String`, not assumed to be one `Char`;
- use `if` and `when` as expressions that produce values;
- interpolate values into strings and read a basic Kotlin compiler type error;
- implement a fourth-letter variation without copying a finished declaration.

## Prerequisites

- Module 00 passed.
- The shared test task runs.
- You know the exact `<shared-module>` and `<package-root>` names.

## Retrieval warm-up

1. Which source set should contain a test of platform-independent letter logic?
2. What is the difference between that source set and its Kotlin package?
3. If a type error exists in `commonTest`, should production Android source need to change?

## Why the app needs this now

Alphabet content appears simple, but it exposes real modeling decisions immediately. A letter has stable authored facts, some displayed forms contain multiple Unicode code points, and UI descriptions are derived from those facts. Kotlin makes these types and transformations explicit.

We begin in `commonTest` so each language experiment is executable, repeatable, and separate from production design.

## Mental model

Kotlin favors expressions and read-only references:

```text
input facts -> expression -> derived value -> assertion
```

- `val` means the reference cannot be reassigned.
- `var` means the reference may be reassigned.
- neither keyword makes a referenced mutable object deeply immutable;
- a declaration's type limits which values can be assigned and which operations are available.

Default to `val`. Use `var` when the lesson's behavior truly changes one local value, not because JavaScript commonly uses `let`.

### Hebrew text is not safely “one character”

Kotlin `Char` represents one UTF-16 code unit. Displayed Hebrew such as a base letter plus a dagesh or vowel mark may contain multiple code points/code units. In this product, a renderable glyph or pointed form is text: model it as `String` unless a later algorithm has a proven need for Unicode segmentation.

## React Native bridge

| TypeScript/JavaScript | Kotlin | Difference that matters |
|---|---|---|
| `const glyph = "א"` | `val glyph = "א"` | Kotlin infers a static `String` type. |
| `let attempts = 0` | `var attempts = 0` | Kotlin will not later accept a `String` in that variable. |
| ternary expression | `if` expression | Kotlin has no Java-style ternary; `if` itself returns a value. |
| object/string coercion in template | `"$name: $glyph"` | Kotlin templates are typed expressions, not permission for arbitrary coercion. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Declaration | A named value or function introduced to the compiler. |
| Inference | Compiler determination of a type from the assigned expression. |
| Explicit type | A written constraint such as `val order: Int`. |
| Expression | Code that evaluates to a value. |
| Statement | Code performed for its effect; not every statement yields a useful value. |
| String template | A string containing `$name` or `${expression}`. |
| Smart cast | A compiler-proven narrowing of a type; introduced more fully later. |

## Predict before running

Type this small demonstration into the lesson test file, but do not run it yet:

```kotlin
val glyph = "ב"
var attempts: Int = 1
val status = if (attempts == 1) "first attempt" else "review"
attempts += 1
```

Predict:

1. the inferred type of `glyph`;
2. the value of `status`;
3. the final value of `attempts`;
4. the compiler result if the next line is `attempts = "two"`.

Explain each prediction before running the test.

## Minimal demonstration

Create:

`<shared-module>/src/commonTest/kotlin/<package-root>/learning/kotlin/LetterBasicsTest.kt`

Use your real package declaration and the generated project's `kotlin.test` imports. Inside one test, type the prediction snippet and assertions for your predicted values.

Then add one derived expression:

```kotlin
val label = "$glyph — $status"
```

This is the complete demonstration. Do not build a `Letter` class yet.

Expected result: the shared test compiles and passes. Uncomment the incorrect assignment once, read the compiler error, and remove it manually.

## Guided lab

### Step 1 — Represent authored facts

In a second test, declare facts for Alef:

- ID: `alef`
- order: `1`
- glyph: `א`
- Latin name: `alef`
- whether it is a final form: false

Choose where an explicit type improves the exercise and where inference is obvious. Before compiling, say why every reference is a `val`.

### Step 2 — Derive a classification

Use an `if` expression to derive either `"base"` or `"final"` from the Boolean. Store the result in a `val` and assert it.

Expected result: no variable needs reassignment.

### Step 3 — Use `when` as an expression

For Bet, use its number of authored sounds to derive a label:

```text
0 -> "no sound recorded"
1 -> "one sound"
otherwise -> "multiple sounds"
```

Choose the value that represents the count, type the `when` expression yourself, and assert the result for Bet.

### Step 4 — Notice the glyph boundary

Compare the strings `"ב"` and `"בּ"` in the debugger or with length observations. Do not write product behavior that depends on the observed numeric lengths.

Explain why “visible letter form” and Kotlin `Char` are not equivalent product concepts.

### Step 5 — Produce summaries

Create one summary string for each of Alef, Bet, and Gimel using templates. Each summary must contain order, glyph, Latin name, and your derived sound-count label.

Keep the declarations separate for now. Repetition is evidence that will motivate functions and models in later lessons.

## Independent task

Without copying one of the three declaration blocks line for line, add Dalet's facts and summary:

- ID `dalet`
- order `4`
- glyph `ד`
- Latin name `dalet`
- one authored sound
- not a final form

Before typing, write the expected summary. Add assertions for the facts and derived classification.

### Acceptance criteria

- The file is in `commonTest` and uses the real package root.
- Core declarations are typed by the learner.
- Read-only facts use `val`.
- At least one explicit `String`, `Int`, and `Boolean` type appears for instructional clarity.
- Both `if` and `when` are used as value-producing expressions.
- Glyphs and pointed/display forms are `String`, not `Char`.
- The Dalet variation is independently implemented and tested.
- No Compose, Android, iOS, Room, Koin, coroutine, or serialization imports are present.

## Test and debugging plan

1. Run only `LetterBasicsTest`.
2. If compilation fails, identify declaration name, expected type, and actual type.
3. If an assertion fails, write the expected and actual values before editing.
4. Deliberately create and interpret one type mismatch.
5. Rerun the single test after correcting it.

Question before help: did the compiler reject an invalid program, or did a valid program produce the wrong value? Those require different debugging paths.

## Hint ladder

<details>
<summary>Hint 1 — Expressions</summary>

The result of `if` or `when` can be assigned directly to a `val`; you do not need a mutable result variable.

</details>

<details>
<summary>Hint 2 — `when` shape</summary>

Use the sound count as the subject, then provide branches for `0`, `1`, and `else`.

</details>

<details>
<summary>Hint 3 — Test shape</summary>

Arrange facts, compute a derived value, then assert the exact expected value. Keep all of this inside one `@Test` function if setup remains tiny.

</details>

No complete Dalet solution is published before an attempt.

## Teach-back

Explain why this lesson uses `String` for Hebrew display forms, why most references are `val`, and how expression-oriented `if`/`when` avoids unnecessary mutation.

## Exit ticket

1. Does `val` make every object deeply immutable?
2. When does an explicit type improve code that Kotlin could infer?
3. What does an `if` expression return?
4. Why is `"בּ"` not safely modeled as a `Char`?
5. What information in a compiler type mismatch is most useful first?

## Review rubric

This lesson assesses Kotlin syntax, type reasoning, compiler feedback, Unicode modeling, and independent implementation. Passing is based on the Dalet variation and explanation, not the copied demonstration.

## Completion evidence

- [ ] Prediction is recorded before execution.
- [ ] Alef, Bet, and Gimel test lab passes.
- [ ] Deliberate type mismatch is interpreted correctly.
- [ ] Independent Dalet variation passes.
- [ ] Learner explains `val`, expressions, and the glyph `String` decision.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 01.02 turns repeated derivations into functions and models optional content safely. `val`, expression bodies, and type inference will appear in every later Kotlin lesson.

## References

- [Kotlin basic syntax](https://kotlinlang.org/docs/basic-syntax.html)
- [Kotlin basic types](https://kotlinlang.org/docs/basic-types.html)
- [Kotlin control flow](https://kotlinlang.org/docs/control-flow.html)
