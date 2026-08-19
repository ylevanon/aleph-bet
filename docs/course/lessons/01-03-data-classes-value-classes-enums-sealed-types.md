# Lesson 01.03 — Data classes, value classes, enums, and sealed types

Status: Ready after Lesson 01.02

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 75–100 minutes

## Product outcome

Alef, Bet, and Gimel are represented by domain-shaped Kotlin types that prevent unrelated strings from being confused and make important letter-form cases explicit.

Use the Module 01 path convention established in Lesson 01.01. Keep this lesson's first draft in the learning test package; promote reviewed declarations to `alphabet/domain` only in Lesson 01.06.

## Learning outcomes

By the end, you can:

- use a data class for immutable values whose properties define equality;
- wrap a primitive in a value class to create a distinct domain type;
- use an enum for a fixed set of singleton choices;
- use a sealed interface when a closed family has cases with different data;
- use exhaustive `when` expressions over enums and sealed types;
- reject invalid states at construction boundaries;
- compare type designs by the invalid states they permit.

## Prerequisites

- Lesson 01.02 passed.
- You can write functions with explicit types and handle a nullable value without `!!`.

## Retrieval warm-up

1. Why is an optional base-letter relationship a better nullable candidate than a sounds list?
2. If a helper always produces fallback text, should its return type remain `String?`?
3. Name two different product values that might both be raw `String` but should not be interchangeable.

## Why the app needs this now

Loose primitives make invalid calls easy:

```text
loadLetter(glyph)       // compiles if both glyph and ID are String
playSound(letterId)     // compiles if both sound ID and letter ID are String
```

They also allow invalid combinations, such as a final form without a base-letter relationship. Kotlin's type system can move those failures from a distant screen into compilation or construction.

The goal is not to replace every string with a class. Add a type when it communicates a domain boundary or excludes a meaningful mistake.

## Mental model

Choose a type from the shape of valid values:

| Need | Candidate | Reason |
|---|---|---|
| Several named properties define a value | `data class` | Generated equality, `copy`, destructuring, and readable output. |
| One primitive needs a distinct domain identity | `value class` | `LetterId` cannot be passed where an arbitrary `String` is required without explicit conversion. |
| Fixed singleton choices with the same shape | `enum class` | Every entry is one instance of the same type. |
| Fixed cases carry different required data | `sealed interface`/class | Each subtype can require its own properties; `when` can be exhaustive. |

### Model states, not database rows

The content JSON is an authoring shape. A domain type should express what application behavior requires, even when that shape differs from JSON. DTO mapping arrives in Lesson 01.05 and serialization in Module 04.

## React Native bridge

| TypeScript pattern | Kotlin type | Where Kotlin is stricter |
|---|---|---|
| object type/interface | data class | Construction, equality, nullability, and copying are compiler-defined. |
| branded string | value class | A real Kotlin type participates in overload/type checking. |
| string union | enum or sealed hierarchy | `when` can be checked exhaustively; sealed cases may carry different data. |
| discriminated union | sealed interface with data classes/objects | Implementations are constrained by package/module rules. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Structural/value equality | Values compare by meaningful properties rather than object identity. |
| Data class | Class intended primarily to hold data, with generated value-oriented members. |
| Value class | A distinct type wrapping one underlying value. |
| Invariant | Rule that must hold for every valid instance. |
| Enum entry | One predefined singleton value in an enum. |
| Sealed hierarchy | Closed family of known direct subtypes. |
| Exhaustive `when` | A `when` that covers every possible case and needs no `else`. |

## Predict before running

Read this minimal, unrelated-to-production example:

```kotlin
data class DisplayName(val pointed: String, val latin: String)

val first = DisplayName(pointed = "אָלֶף", latin = "alef")
val second = DisplayName(pointed = "אָלֶף", latin = "alef")
val renamed = first.copy(latin = "aleph")
```

Predict:

1. whether `first == second`;
2. whether `first === second`;
3. whether `first` changes when `renamed` is created;
4. the Latin value in `renamed`.

Then explain why value equality is useful in tests and immutable UI state.

## Minimal demonstration

In `LetterTypesTest.kt`, type `DisplayName` and its predictions as assertions. Then create an enum with two lesson-only choices, such as `ReadingDirection.LEFT_TO_RIGHT` and `RIGHT_TO_LEFT`, and write an exhaustive `when` that returns a label.

This demonstration shows mechanics. It is not the Aleph Bet production model.

Expected result: the compiler accepts the `when` without `else` because all enum entries are covered. Add a third entry briefly and observe the compile failure before adding its branch.

## Guided lab

### Step 1 — Protect letter identity

Design a `LetterId` value class wrapping `String`.

Constraints:

- blank identifiers are invalid;
- construction makes the validation boundary visible;
- callers can deliberately access the underlying authored value when mapping or display requires it;
- a `LetterId` cannot be passed as an arbitrary `String` accidentally.

Current Kotlin uses `@JvmInline value class` for value classes that include a JVM/Android compilation. Type the declaration yourself after reading the official value-class section.

Test valid `alef` and invalid blank input. Read the failure produced by your chosen invariant mechanism.

### Step 2 — Represent a sound value

Design a data class for one authored letter sound. Decide which of these are required versus optional:

- stable sound ID such as `b`;
- human description such as `b with dagesh`;
- pointed sample such as `בָּ`;
- future audio resource identity.

For this lesson, do not invent an audio path that the reviewed content does not yet guarantee. Test value equality and one `copy` operation.

### Step 3 — Compare enum and sealed form designs

Consider these product constraints:

- a standard form requires a glyph;
- a final form requires a glyph **and** a base `LetterId`;
- future pointed/alternate forms may require a sound reference;
- callers should not ask a standard form for a fake base ID.

First sketch an enum design. List the nullable properties or external maps it would need.

Then sketch a sealed `LetterForm` family whose cases carry their required data. Choose the simpler valid model and explain the tradeoff. The expected direction is a sealed family if case-specific data is required, but your case names are not prescribed.

### Step 4 — Make behavior exhaustive

Write a function that accepts your `LetterForm` type and returns a human-readable classification. Use `when` without `else`.

Add a new form subtype temporarily. Observe what the compiler requires at the `when` call site, then implement the new branch or remove the experimental subtype.

### Step 5 — Assemble three letter values

Design a data class that can represent the reviewed facts needed for Alef, Bet, and Gimel:

- stable ID;
- order;
- glyph/name information;
- a non-empty or intentionally empty list of sound values;
- form information.

State and test at least three invariants. Candidates include positive order, non-blank glyph/name, stable ID validation, and consistency between a primary glyph and form. Avoid invariants based on editorial claims we have not reviewed.

Construct Alef, Bet, and Gimel. Bet must represent its distinct authored `b` and `v` sound samples without flattening them into one ambiguous string.

## Independent task

Add one new case that forces a type-design choice: a final Kaf form with ID `final_kaf`, glyph `ך`, and base ID `kaf`.

Do not add all Kaf content. The task is to prove that your form model requires the base relationship for a final form and does not require it for a standard form.

### Acceptance criteria

- `LetterId` is a distinct validated type.
- Data classes are used for values whose properties define equality.
- An enum is used only for same-shaped singleton choices, or its omission is justified.
- A sealed type represents at least one family with case-specific data.
- `when` over that family is exhaustive without `else`.
- A final form cannot be constructed without its base `LetterId`.
- Standard forms do not carry a meaningless nullable base ID.
- Alef, Bet, and Gimel values and the final-Kaf form test pass.
- No framework, persistence, serialization, or platform annotations leak into the domain exercise.

## Test and debugging plan

1. Test each invariant at its construction boundary.
2. Test data-class equality and `copy` separately from domain rules.
3. Trigger one non-exhaustive `when` compile error and explain it.
4. Attempt one invalid cross-type call using `LetterId` and `String`; interpret the compiler error, then remove it.
5. Run the full Module 01 test package after the focused test passes.

## Hint ladder

<details>
<summary>Hint 1 — Choose by valid states</summary>

If two cases require different properties, ask whether one class with nullable fields permits combinations the product says are impossible.

</details>

<details>
<summary>Hint 2 — Value class</summary>

The wrapper has one primary constructor property. Put validation in initialization and expose the value intentionally.

</details>

<details>
<summary>Hint 3 — Sealed shape</summary>

Define a sealed interface with the properties common to all forms. Use a data class for each form whose additional fields are required by that case.

</details>

No complete domain model is published before your design attempt.

## Teach-back

Defend one use each of data class, value class, enum, and sealed type—or explain why the domain did not need one of them. Focus on invalid states prevented, not syntax preference.

## Exit ticket

1. Why is `LetterId` stronger than `typealias LetterId = String`?
2. When does a data class use value equality?
3. When is an enum less suitable than a sealed interface?
4. What causes a `when` expression over a sealed type to lose exhaustiveness?
5. Which invalid state does your final-form design prevent?

## Review rubric

This lesson assesses Kotlin type modeling, invariants, exhaustiveness, and simplicity. More types are not automatically better; every wrapper or hierarchy must prevent a concrete mistake or clarify behavior.

## Completion evidence

- [ ] Equality predictions pass.
- [ ] `LetterId` validation and cross-type behavior are tested.
- [ ] Alef, Bet, and Gimel can be represented.
- [ ] Final-Kaf independent case proves the base relationship.
- [ ] Exhaustiveness failure is interpreted.
- [ ] Type choices are defended in product terms.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 01.04 places these values in collections and builds stable lookups and transformations. Sealed state modeling returns in the lesson engine and presentation modules.

## References

- [Kotlin data classes](https://kotlinlang.org/docs/data-classes.html)
- [Kotlin inline value classes](https://kotlinlang.org/docs/inline-classes.html)
- [Kotlin enum classes](https://kotlinlang.org/docs/enum-classes.html)
- [Kotlin sealed classes and interfaces](https://kotlinlang.org/docs/sealed-classes.html)
