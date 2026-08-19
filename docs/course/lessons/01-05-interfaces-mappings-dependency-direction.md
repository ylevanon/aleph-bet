# Lesson 01.05 — Interfaces, mappings, and dependency direction

Status: Ready after Lesson 01.04

Module: 01 — Kotlin through the alphabet domain

Estimated focused time: 75–100 minutes

## Product outcome

Alphabet consumers depend on a domain repository contract, while source-shaped content and its mapper remain outside the domain. A fake proves the contract without JSON, files, Room, Koin, or a running application.

Use the Module 01 path convention established in Lesson 01.01.

## Learning outcomes

By the end, you can:

- declare and implement a Kotlin interface;
- explain dependency direction using imports and constructor parameters;
- separate source-shaped DTOs from validated domain models;
- map raw IDs, optional fields, and collections at one boundary;
- use a fake implementation in a test without dependency injection tooling;
- decide whether validation belongs to DTO parsing, mapping, or domain construction.

## Prerequisites

- Lesson 01.04 passed.
- The test-lab model has validated IDs, form cases, and collection rules.

## Retrieval warm-up

1. Why can `associateBy` hide duplicate IDs?
2. Why do we retain an ordered list after creating an ID lookup map?
3. Which type should reject a blank `LetterId`: a screen, a repository caller, or the construction/mapping boundary?

## Why the app needs this now

The application needs letter facts, but a screen should not know whether those facts came from bundled JSON, a test fake, or a future reviewed content source. It should also never receive half-validated DTOs shaped around file keys.

A repository contract names the domain capability. A data implementation handles a particular source. A mapper converts and validates between their models.

This separation is justified by present needs: tests require a fake, and bundled content has a source schema different from domain invariants. It is not being added merely because an API might exist someday.

## Mental model

Dependency direction points toward stable product meaning:

```text
presentation or domain behavior
            |
            v
   AlphabetRepository       domain contract
            ^
            |
BundledAlphabetRepository   data implementation
            |
       DTO + mapper          source boundary
```

The implementation depends on the contract and domain types. The contract does not import the implementation, JSON library, file path, Compose, or Room.

### Contract versus implementation

“Repository” can refer to two related declarations:

- the interface describing what alphabet consumers can ask for;
- a concrete implementation fulfilling that interface from bundled content.

Only the implementation interacts with source details. Room will later back `ProgressRepository`; it does not follow that every repository is a Room interface.

## React Native bridge

| Familiar pattern | Kotlin/KMP concept | Difference that matters |
|---|---|---|
| Hook returns Convex query data | ViewModel/use case depends on repository contract later | A repository is an explicit constructor dependency, not an implicit hook call tied to rendering. |
| TypeScript interface for service | Kotlin interface | Kotlin implementation and return types are compiler-checked across source sets. |
| API response type mapped for UI | DTO-to-domain mapper | The domain type enforces invariants and is independent of transport/storage annotations. |
| mocked module/provider | directly constructed fake | Most domain tests do not need Koin or a global module registry. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Interface | Contract declaring operations/properties without choosing one source implementation. |
| Implementation | Concrete type satisfying an interface. |
| DTO | Data-transfer/source object mirroring authored or serialized fields. |
| Domain model | Valid application concept used by product rules. |
| Mapper | Boundary function converting one representation to another. |
| Dependency direction | Which layer imports/knows another layer. |
| Fake | Small working test implementation with controlled behavior. |
| Constructor injection | Supplying a dependency when creating an object. |

## Predict before running

Consider:

```kotlin
interface NameSource {
    fun names(): List<String>
}

class StaticNameSource(
    private val values: List<String>,
) : NameSource {
    override fun names(): List<String> = values
}
```

Predict:

1. which declaration can be used as the parameter type for code that accepts any name source;
2. whether `StaticNameSource` may have private implementation details not present on the interface;
3. what happens if `override` is removed;
4. whether a test needs Koin to pass `StaticNameSource` to a constructor.

## Minimal demonstration

In `AlphabetMappingTest.kt`, type the `NameSource` example and a tiny consumer function accepting `NameSource`. Pass a directly constructed fake/static implementation and assert the result.

Then add a second implementation with different values without changing the consumer.

Expected result: the consumer imports only the contract; construction chooses the implementation.

## Guided lab

### Step 1 — Design the alphabet contract from use

Start from two current product questions:

- retrieve letters in authored order;
- resolve a letter by stable ID.

Propose the smallest `AlphabetRepository` interface that answers them. Decide whether both operations are necessary or whether one can be derived from the other without hiding meaningful validation/performance behavior.

For this language module, keep the contract synchronous. We have not yet learned coroutines, and bundled in-memory test data does not justify pretending to be asynchronous. Module 04 will revisit the production loading boundary with evidence; Module 06 teaches `suspend` and `Flow` explicitly.

### Step 2 — Define a source-shaped DTO

In the test/data area, define a DTO that resembles only the subset of `asset-pack/data/alphabet.json` needed for Alef, Bet, and Gimel:

- primitive ID and order;
- glyph and names;
- sound description strings;
- optional forms/sound samples as required by your domain model;
- `finalForm` and optional `baseLetterId` if your mapping exercise includes them.

Use raw `String`, `Int`, `Boolean`, nullable values, and lists. Do not put serialization annotations on it yet.

Explain why DTO construction may temporarily permit a combination the domain rejects.

### Step 3 — Map one valid entry

Write a mapper from the DTO to your reviewed domain type. It must:

- construct `LetterId` rather than leak the raw string;
- preserve authored sound and form order;
- choose the correct sealed form case;
- enforce required final-form relationships;
- return only a valid domain value or fail with a useful message.

Map Alef first as the guided example. Test exact domain fields, not only that no exception occurred.

### Step 4 — Map Bet's richer shape

Map Bet with both `b` and `v` authored variants. This is the first transfer beyond the simple entry.

Before implementing, identify which DTO lists must stay aligned. If parallel arrays permit mismatched descriptions and samples, decide whether the DTO should use a nested sound-sample DTO instead.

The mapper should not guess missing educational content.

### Step 5 — Implement a fake repository

Create a fake that accepts a prevalidated ordered list through its constructor and implements your contract. Test:

- authored-order retrieval;
- successful Bet lookup;
- missing ID behavior chosen by the contract.

Construct the fake directly. Koin Annotations arrive only when runtime object assembly exists in Module 06.

### Step 6 — Audit imports and packages

For every declaration, state its eventual owner:

| Declaration | Expected owner |
|---|---|
| `Letter`, `LetterId`, `LetterForm` | `alphabet/domain` |
| `AlphabetRepository` contract | `alphabet/domain` |
| source DTO | `alphabet/data` |
| DTO-to-domain mapper | `alphabet/data` |
| bundled repository implementation | `alphabet/data` |
| fake used only by tests | test source set/package |

Verify that domain code imports no data declarations.

## Independent task

Add Gimel mapping and three corrupted DTO tests:

1. blank stable ID;
2. non-positive order;
3. final form marked true with no base-letter ID.

For each, predict whether the failure comes from DTO construction, mapping, or domain construction. Then ensure the observed failure message names the invalid field or relationship well enough to repair authored content.

### Acceptance criteria

- The repository interface is owned by the alphabet domain package.
- DTOs and mapper are owned by alphabet data.
- Domain imports do not point toward data or frameworks.
- Alef, Bet, and Gimel map to validated domain values.
- Bet's variants are not accidentally paired by fragile parallel indices if a nested DTO can express the relationship.
- Corrupted inputs fail at a deliberate boundary with useful context.
- A fake proves the contract through direct construction.
- No JSON/serialization, Compose, Room, Koin, Android, or iOS dependency is introduced.

## Test and debugging plan

1. Test one valid simple DTO.
2. Test one valid richer DTO.
3. Test every domain invariant through corrupted DTO input.
4. Test fake repository order and missing lookup behavior.
5. Audit imports from domain outward.
6. If a mapping test fails, state whether raw extraction, conversion, validation, or repository behavior is responsible before editing.

## Hint ladder

<details>
<summary>Hint 1 — Dependency direction</summary>

Put the interface beside the domain values it returns. Put knowledge of source field names in data.

</details>

<details>
<summary>Hint 2 — Rich sound DTO</summary>

If an ID, description, and pointed sample describe one sound, one nested DTO can keep them together before mapping.

</details>

<details>
<summary>Hint 3 — Mapper phases</summary>

Read and validate the raw fields, choose the form subtype, map each nested sound, then construct the final domain value.

</details>

No complete mapper or repository solution is published before an attempt.

## Teach-back

Trace a Bet DTO through mapping into a domain value and then through a fake repository consumer. Name which layer knows raw field names, which owns invariants, and which chooses the implementation.

## Exit ticket

1. Why is `AlphabetRepository` owned by the domain rather than bundled-content data?
2. What information may a DTO permit that the domain rejects?
3. Why does a mapper belong at the source boundary?
4. Does a repository interface necessarily interact with Room?
5. Why can a unit test construct a fake without Koin?

## Review rubric

This lesson assesses interface design, dependency direction, DTO/domain separation, validation placement, fakes, and simplicity. Passing requires clean imports and corrupted-input evidence, not merely a repository-shaped class.

## Completion evidence

- [ ] Interface predictions pass.
- [ ] Minimal alphabet contract is defended.
- [ ] Alef, Bet, and Gimel map successfully.
- [ ] Corrupted DTO failures identify useful boundaries.
- [ ] Fake repository behavior passes without DI tooling.
- [ ] Package/import audit shows domain independence.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Lesson 01.06 promotes the reviewed core types and contract to production source, adds a coherent test suite, and requires an independent Dalet addition. Module 04 returns to the data implementation with real serialization and resources.

## References

- [Kotlin interfaces](https://kotlinlang.org/docs/interfaces.html)
- [Kotlin visibility modifiers](https://kotlinlang.org/docs/visibility-modifiers.html)
- [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
