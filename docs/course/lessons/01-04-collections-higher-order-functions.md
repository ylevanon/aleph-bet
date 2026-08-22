# Lesson 01.04 — Collections and higher-order functions

Status: Passed for progression on 2026-08-20; advanced operations deferred to their first product use

Course adaptation: the learner demonstrated the collection distinctions needed for current work and already transfers Java collection knowledge. The application now uses an ordered read-only `List<Letter>`, sorting, iteration, and typed lookup reasoning. `mapNotNull`, grouping, duplicate-ID enforcement, missing-reference validation, and independent transformations will be retrieved when bundled content and session queues create real cases. The remaining drills on this page are reference material, not a gate before Compose.

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 70–90 minutes

## Product outcome

The three-letter domain sample can be looked up by stable ID, filtered and summarized without mutation, and transformed into UI-independent alphabet/form relationships.

Use the Module 01 path convention established in Lesson 01.01.

## Learning outcomes

By the end, you can:

- choose among read-only `List`, `Set`, and `Map` based on access behavior;
- use `map`, `filter`, `associateBy`, `mapNotNull`, and `groupBy`;
- read and write lambda expressions using `it` and named parameters;
- distinguish a collection transformation from mutation;
- enforce duplicate-ID and missing-reference rules at a mapping boundary;
- implement a new transformation without imperative indexing.

## Prerequisites

- Lesson 01.03 passed.
- `LetterId`, letter values, sounds, and form types exist in the test lab.

## Retrieval warm-up

1. Which invalid state does your final-form subtype prevent?
2. Why is a sounds collection normally non-null even when it is empty?
3. What makes a `when` expression exhaustive for a sealed type?

## Why the app needs this now

An alphabet screen renders an ordered collection; navigation resolves a stable ID; validation finds duplicates and broken relationships; practice will later select subsets. Those are collection operations, not reasons to put loops and mutable arrays inside a ViewModel.

Kotlin's collection APIs let us express the result we want while preserving the original authored order and values.

## Mental model

Choose by the question:

| Product question | Collection shape |
|---|---|
| Render authored order, including possible repeated categories | `List<Letter>` |
| Test membership or enforce unique values | `Set<LetterId>` |
| Resolve one value by unique stable key | `Map<LetterId, Letter>` |
| Group multiple values under a key | `Map<Key, List<Value>>` |

Most standard transformations return a new collection:

```text
source List
    -> filter predicate
    -> map transformation
    -> new List
```

A read-only `List` interface prevents mutation through that reference; it does not promise the underlying object can never change elsewhere. For domain inputs, construct and expose read-only collections and avoid shared mutable backing state.

## React Native bridge

| JavaScript/TypeScript | Kotlin | Difference that matters |
|---|---|---|
| `array.map(x => ...)` | `list.map { value -> ... }` | Lambda syntax and type inference differ, but the transformation idea transfers. |
| `array.filter(Boolean)` | `mapNotNull { ... }` or explicit predicate | Kotlin nullability makes the retained type precise. |
| object/`Map` index | `associateBy` then map lookup | Duplicate keys overwrite unless you validate; that can hide authored errors. |
| spread copy | read-only collection transformation | Kotlin distinguishes read-only and mutable collection interfaces. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Higher-order function | Function that accepts or returns a function. |
| Lambda | Function value written inline, such as `{ letter -> letter.id }`. |
| Predicate | Function returning `Boolean`, commonly used by `filter`. |
| Transformation | Function converting one value/collection shape to another. |
| Association | Building key-value pairs or a map from elements. |
| Grouping | Collecting elements under keys, producing lists of matches. |
| Duplicate key | Two input values map to the same unique key. |

## Predict before running

Given IDs in authored order:

```kotlin
val ids = listOf(
    LetterId("alef"),
    LetterId("bet"),
    LetterId("gimel"),
)

val labels = ids
    .filter { it.value != "bet" }
    .map { it.value.uppercase() }
```

Predict:

1. the exact `labels` value and order;
2. whether `ids` changes;
3. the inferred lambda parameter type;
4. what changes if `map` and `filter` are reversed.

Then replace `it` with a named parameter and decide which version reads better here.

## Minimal demonstration

In `AlphabetCollectionsTest.kt`, type the prediction and assertions. Add:

```kotlin
val byId = ids.associateBy { id -> id }
```

Assert a successful Bet lookup and a missing Dalet lookup. Do not force the missing result with `!!`; observe that map access returns a nullable value.

Expected result: the original list is unchanged and missing lookup is handled explicitly.

## Guided lab

### Step 1 — Render-order summaries

Create a read-only list containing the Alef, Bet, and Gimel values from Lesson 01.03 in authored order. Use `map` to produce a list of display summaries.

Assert the whole resulting list so order is part of the behavior.

### Step 2 — Filter by behavior

Use `filter` for at least two questions:

- which letters have multiple sounds;
- which entries are final forms.

For the three-letter sample, one result may be empty. An empty result is useful evidence and should not be `null`.

### Step 3 — Build a stable lookup

Use `associateBy` to build `Map<LetterId, Letter>`. Before accepting it, decide what duplicate IDs should mean.

`associateBy` keeps one value for a duplicate key; silent replacement is unsafe for authored curriculum. Add a validation that proves source size and unique-key count agree, or otherwise detects duplicates before returning the map.

Test the failure with a duplicated Bet.

### Step 4 — Resolve optional relationships

Create a small collection containing at least one standard form and the final-Kaf form from Lesson 01.03. Use a transformation to return only base-letter relationships from cases that have them.

Prefer a type-aware sealed `when` plus `mapNotNull`, or another clear transformation. Do not add fake null properties to standard forms merely to simplify the loop.

Expected result: the relationship output contains final Kaf to Kaf and omits standard forms.

### Step 5 — Group intentionally

Group the three letters by a derived sound-count category such as `ONE` and `MULTIPLE`. Decide whether that category deserves an enum from Lesson 01.03 or is clearer as a private test value.

Assert group contents without assuming map iteration order unless the chosen API guarantees what the product needs.

## Independent task

Write a pure function that accepts an alphabet list and returns a validated ordered list of pairs connecting every final form to its base entry.

Required behavior:

- output follows the final forms' authored input order;
- standard forms are omitted;
- a missing base ID fails clearly;
- duplicate letter IDs fail clearly before relationships are produced;
- the input list is not mutated.

Use the final-Kaf case for success and create one broken-reference case for failure.

### Acceptance criteria

- Function input and output use read-only collection interfaces.
- Stable `LetterId` keys drive lookup.
- `map`, `filter`, `associateBy`, `mapNotNull`, or `groupBy` are used where they make intent clearer.
- No `!!`, unchecked index lookup, or mutable global collection appears.
- Duplicate IDs and missing base references are tested.
- Authored ordering behavior is asserted.
- The transformation remains pure Kotlin in `commonTest`.

## Test and debugging plan

1. Test happy-path ordering.
2. Test an empty input.
3. Test duplicate IDs.
4. Test a missing final-form base.
5. If a map lookup becomes nullable, explain whether absence is valid output or invalid authored content before choosing fallback versus failure.
6. Rerun all Module 01 tests after the focused collection test passes.

## Hint ladder

<details>
<summary>Hint 1 — Separate validation</summary>

Validate uniqueness and create a lookup before transforming final forms. Each phase should answer one question.

</details>

<details>
<summary>Hint 2 — Preserve order</summary>

Transform the original ordered list of final-form entries; use the map only to resolve each base.

</details>

<details>
<summary>Hint 3 — Shape</summary>

One possible pipeline is: validate IDs, build `byId`, select final-form cases, then map each to a pair after resolving its required base.

</details>

No full implementation is published before an attempt.

## Teach-back

Explain why the app retains an ordered `List<Letter>` even after creating a `Map<LetterId, Letter>`, and why `associateBy` alone is insufficient validation for authored IDs.

## Exit ticket

1. When is a `Map` better than repeatedly searching a list?
2. Does `filter` mutate its source collection?
3. What does `mapNotNull` guarantee about its result element type?
4. How can `associateBy` hide a duplicate-key error?
5. Why should final-form output order be derived from the source list rather than map iteration?

## Review rubric

This lesson assesses Kotlin collection fluency, nullable lookup reasoning, validation, order preservation, and independent transformation design.

## Completion evidence

- [ ] Collection-chain predictions pass.
- [ ] Ordered summaries and filters pass.
- [ ] Stable lookup rejects duplicate IDs.
- [ ] Relationship transformation handles success and missing-base failure.
- [ ] Independent task preserves order without mutation.
- [ ] Learner explains list/map coexistence.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 01.05 puts a repository contract in front of source-shaped content and maps raw fields into the validated types. Collection validation returns in Module 04's full content loader and Module 05's lesson queues.

## References

- [Kotlin collections overview](https://kotlinlang.org/docs/collections-overview.html)
- [Collection transformation operations](https://kotlinlang.org/docs/collection-transformations.html)
- [Filtering collections](https://kotlinlang.org/docs/collection-filtering.html)
- [Grouping collections](https://kotlinlang.org/docs/collection-grouping.html)
