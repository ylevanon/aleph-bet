# Lesson 01.06 — `kotlin.test` and the Kotlin checkpoint

Status: Ready after Lesson 01.05

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 90–120 minutes

## Product outcome

Reviewed alphabet domain declarations and the repository contract live in `commonMain`; focused tests in `commonTest` prove three-letter construction, invariants, mappings, collection behavior, and an independently added Dalet. This is Module 01's product gate.

## Learning outcomes

By the end, you can:

- organize `kotlin.test` tests around behavior and failure cases;
- choose assertions that reveal useful expected/actual evidence;
- move reviewed declarations from a learning lab into production packages without moving test fixtures into the app;
- run a focused test and the full shared test suite;
- interpret one compiler failure and one assertion failure;
- implement a fourth-letter case from requirements rather than transcription;
- explain the Module 01 domain design without framework vocabulary.

## Prerequisites

- Lessons 01.01–01.05 passed or submitted for checkpoint review.
- The shared test task is reliable.
- The coach has reviewed the test-lab model enough to identify declarations ready for production.

## Retrieval warm-up

1. Why does the domain own the `AlphabetRepository` interface?
2. What does `associateBy` do with duplicate keys, and how did you prevent silent loss?
3. Which type decision prevents a final form without a base relationship?
4. Why has Koin not been needed yet?

## Why the app needs this now

The earlier lessons isolated language mechanisms. The app now needs a small, trusted core that later Compose, content loading, ViewModels, and Room code can depend on.

Promotion to `commonMain` is not “copy everything from the lab.” We keep only product declarations with justified names and invariants. Demonstration types, temporary helpers, and test fixtures remain in tests or are removed.

## Mental model

A useful test states a behavior boundary:

```text
Arrange a meaningful input
Act through the public contract
Assert an observable result or failure
```

Tests should survive harmless implementation changes. Prefer:

- `assertEquals(expected, actual)` for exact values;
- `assertTrue`/`assertFalse` for a single Boolean claim;
- `assertNull`/`assertNotNull` for intentional absence;
- `assertFailsWith<SpecificException>` for rejected invalid construction;
- collection equality when order is part of behavior.

Avoid one giant test with twenty unrelated reasons to fail.

## React Native bridge

| Jest/testing habit | `kotlin.test` habit | Difference that matters |
|---|---|---|
| `describe`/`it` hierarchy | test classes and `@Test` functions | Naming and organization are simpler; generated platform runners execute common tests. |
| `expect(actual).toEqual(expected)` | `assertEquals(expected, actual)` | Expected comes first; types are checked at compile time. |
| `toThrow` | `assertFailsWith<T>` | The expected exception type is explicit. |
| module mock | fake passed to a constructor | Direct construction keeps most domain tests independent of DI/runtime state. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Fixture | Known test data used to create a scenario. |
| System under test | The public behavior being exercised. |
| Assertion | Executable statement of expected behavior. |
| False positive | Test passes even though required behavior is broken. |
| False negative | Test fails despite acceptable behavior, often due to overspecification. |
| Focused test | Smallest test/task relevant to the current change. |
| Regression | Previously working behavior broken by a later change. |

## Predict before running

Suppose a test calls:

```kotlin
assertEquals(
    expected = listOf(LetterId("alef"), LetterId("bet")),
    actual = letters.map { letter -> letter.id },
)
```

Predict the failure output if the actual list is Bet then Alef. Then answer:

1. does the failure prove the values are wrong, the order is wrong, or both?
2. when is ordering a product behavior worth asserting?
3. would `assertTrue(actual.containsAll(expected))` be a stronger or weaker test here?

## Minimal demonstration

Create a temporary `KotlinTestMechanicsTest.kt` and type three tiny tests:

1. one passing `assertEquals`;
2. one `assertFailsWith` around a deliberately invalid `LetterId`;
3. one temporarily failing ordered-list assertion.

Read the failure diff, correct the expectation or setup based on the intended behavior, and rerun. Remove this mechanics file after its learning purpose is complete.

## Guided checkpoint

### Step 1 — Inventory before promoting

Review Module 01's test-lab declarations. Classify each as:

- production domain concept;
- data-boundary concept reserved for Module 04;
- test fixture/fake;
- instructional demonstration to delete.

The coach reviews this list before files move.

### Step 2 — Create production domain files

Under:

`<shared-module>/src/commonMain/kotlin/<package-root>/alphabet/domain/`

create only the reviewed declarations. Likely responsibilities include:

- stable `LetterId`;
- letter-name/sound values actually required by the three-letter slice;
- form cases that preserve valid relationships;
- the `Letter` aggregate or equivalent;
- the minimal `AlphabetRepository` contract.

You choose file grouping based on cohesion. One class per file is not a law, and one giant `Models.kt` file is not automatically simple.

Do not add JSON annotations, Room annotations, Compose types, Koin annotations, Android types, or iOS types.

### Step 3 — Build a behavior-oriented test suite

Under the matching `commonTest` package, organize focused tests for:

1. ID and core invariant validation;
2. standard versus final form construction;
3. Alef, Bet, and Gimel representation;
4. sound/form order preservation;
5. stable-ID lookup and duplicate rejection;
6. missing relationship rejection;
7. fake repository order and lookup contract.

Delete duplicate assertions whose only purpose was syntax practice. Preserve edge cases that protect product behavior.

### Step 4 — Keep mappings at the boundary

The source DTO and mapper exercise may remain in `commonTest` for now, or reviewed source-independent mapping code may be placed in `alphabet/data` if the coach agrees it is already part of the real bundled-content boundary.

Do not add serialization yet. The Module 01 gate is the domain model and Kotlin fluency, not a premature loader.

### Step 5 — Run narrow, then broad

Use this order:

1. one changed test class;
2. all alphabet-domain common tests;
3. the full shared common test suite;
4. shared/common compilation;
5. Android and iOS starter runs only if production common code affected their compilation.

Record exact tasks from your Lesson 00.02 map.

### Step 6 — Review the compiler as a collaborator

Create one reversible call that swaps a raw `String` and `LetterId`, or omit one sealed `when` branch. Predict and capture the error. Remove the deliberate failure manually and verify the same task passes.

Explain which invalid program the compiler prevented.

## Independent checkpoint task — Dalet

From the reviewed content requirements, add Dalet without copying an existing fixture declaration line for line:

```text
ID: dalet
Order: 4
Glyph: ד
Pointed name: דָּלֶת
Latin name: dalet
Sound: ID d, description d, pointed sample דָּ
Final form: no
```

You must decide:

- which domain constructors/cases apply;
- which helper or fixture factory arguments improve clarity;
- which tests prove Dalet was added without weakening existing validation;
- whether any production type change is truly necessary.

The expected architecture is that adding this content-shaped case requires no new subtype, repository method, ViewModel, screen, or platform code.

### Acceptance criteria

- Dalet is constructed using existing reviewed domain types.
- Its exact stable ID, order, glyph, names, sound, pointed sample, and form classification are asserted.
- It appears after Gimel in the ordered four-letter fixture.
- Existing lookup and validation behavior continues to pass.
- No production type is changed solely to special-case Dalet.
- No `!!`, mutable global state, or framework/platform dependency is introduced.
- The full common test suite passes.

## Test and debugging plan

1. Run the new Dalet-focused test first.
2. Run ID/invariant tests.
3. Run collection/lookup tests with all four values.
4. Run fake repository contract tests.
5. Run the full shared test suite.
6. Compile affected targets.
7. If any failure occurs, classify it as compile-time type/invariant, test setup, expected/actual behavior, source-set placement, or build configuration before changing code.

## Hint ladder

<details>
<summary>Hint 1 — Reuse the model</summary>

Dalet is a standard form with one sound. If it requires a new production type, first check whether the existing model overfit Alef or Bet.

</details>

<details>
<summary>Hint 2 — Fixture construction</summary>

Use named constructor arguments or a test fixture helper only when each value remains visible and the helper does not silently invent defaults.

</details>

<details>
<summary>Hint 3 — Assertions</summary>

Assert Dalet's own properties, then assert the ordered ID list for all four entries and successful lookup by `LetterId("dalet")`.

</details>

No complete Dalet implementation is published before an attempt.

## Architecture defense

Answer without code visible:

1. Trace the dependency direction from a future screen to the repository contract to bundled data.
2. Explain why `LetterId` exists instead of passing strings everywhere.
3. Defend your standard/final form model using one invalid state it prevents.
4. Explain why sounds are a list and why authored order matters.
5. Explain where DTO annotations and Room annotations will belong later and why they are absent here.
6. Name one compiler error and one test failure you diagnosed during the module.

## Exit ticket

1. What makes a unit test behavior-oriented rather than implementation-oriented?
2. Why should `assertEquals` receive expected before actual?
3. When should invalid input be tested with `assertFailsWith`?
4. Which Module 01 declarations belong in `commonMain`, and which remain test/data details?
5. What evidence proves the fourth letter was a transfer task rather than transcription?

## Review rubric

Module 01 is evaluated across correctness, Kotlin fluency, type modeling, dependency direction, test quality, compiler/debugging fluency, and simplicity.

The module passes only if the learner can implement and explain the Dalet variation. Green tests alone are insufficient if the design cannot be explained; explanation alone is insufficient without working Kotlin.

## Completion evidence

- [ ] Reviewed domain declarations are in `commonMain`.
- [ ] Demonstration-only code is removed or remains isolated from production.
- [ ] Behavior-oriented common tests pass.
- [ ] Deliberate compiler failure is predicted and explained.
- [ ] Independent Dalet task passes all acceptance criteria.
- [ ] Architecture defense is completed.
- [ ] Review findings are resolved.
- [ ] Checkpoint commit exists in the private application history.
- [ ] Progress record and skill matrix are updated.
- [ ] Module 01 product gate is marked passed or specific revisions are recorded.

## Next retrieval

Module 02 renders hard-coded domain values with shared Compose UI. It will retrieve immutable values, ordered collections, stable IDs, callbacks, and test discipline while introducing recomposition and UI state.

## References

- [Kotlin multiplatform tests](https://kotlinlang.org/docs/multiplatform/multiplatform-run-tests.html)
- [`kotlin.test` API](https://kotlinlang.org/api/core/kotlin-test/)
- [Kotlin test basics](https://kotlinlang.org/docs/jvm-test-using-junit.html)
