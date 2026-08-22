# Lesson-page template

Status: Course authoring template

Last updated: 2026-08-22

Use this structure for full lesson pages. Omit sections that add no value, but preserve the learning sequence: retrieve, understand, predict, build, test, explain, review.

## Example-style contract

Build lessons should preserve the strongest qualities of the referenced notJust.dev written guide:

- begin with what will be built and learned;
- identify the exact files or project area being changed;
- explain the concept before using it;
- proceed through small, numbered implementation steps;
- include focused code blocks that are complete enough to understand in context;
- state what should compile, render, or pass after each meaningful checkpoint;
- clearly mark when the learner should stop reading and attempt the next step;
- place assignments before hints and complete solutions;
- retain written troubleshooting material that can be revisited later.

The personalized course adds one boundary: the worked example must not be the entire feature the learner is meant to implement. Prefer demonstrating one small case in the real Aleph Bet application, then require the learner to implement the next case or production variation. Use an unrelated toy example only when it isolates behavior that would otherwise be obscured by application code.

Configuration boilerplate may be provided directly and labeled as safe to copy. Kotlin code that expresses a new core concept should be typed, completed, or designed by the learner in one representative product case. Repeated applications may move to Pair or Ship mode under the [proof-once rule](method.md#proof-once-and-retrieval-rule).

---

# Lesson XX.XX — Title

Status: Not started

Module: XX — Module name

Estimated focused time: range, not a deadline

## Product outcome

State the visible or testable application capability completed by this lesson.

## Learning outcomes

By the end, the learner can:

- use an observable verb;
- explain an ownership or lifecycle decision;
- demonstrate meaningful transfer when the skill still needs it.

## Prerequisites

- prior lesson or skill;
- required product state;
- narrow reading, if any.

## Work split

State the expected mode and ownership before implementation begins.

- **Learner owns:** the new decision, representative implementation, or debugging evidence required for mastery.
- **Coach may supply:** configuration, scaffolding, fixtures, repeated cases, resource wiring, or mechanical production work.
- **Proof required:** the smallest observable evidence that distinguishes understanding from recognition.

Do not assign a second from-scratch component, modifier chain, or equivalent test merely because the template has an independent-task section.

## Retrieval warm-up

Answer without notes:

1. Prior concept question.
2. Prior concept in a new context.
3. Prediction or placement question.

## Why the app needs this now

Explain the product problem before the technology.

## Mental model

Explain the concept in plain language with a small diagram when useful.

## React Native bridge

| Familiar pattern | Kotlin/KMP concept | Where the analogy breaks |
|---|---|---|
| RN concept | KMP concept | Important difference |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Term | Plain-language definition |

## Predict before running

Present a small snippet, state diagram, import, schema, or lifecycle scenario. Require a prediction and reason before execution.

## Minimal demonstration

Prefer one small, runnable case in the real application. Show only enough code to reveal the new mechanism, state the expected result, and stop before revealing the production variation or complete assignment solution.

## Guided lab

Name the file or source set for every step. Avoid hidden setup or unexplained jumps between code states.

### Step 1 — Intent

Describe what the step proves.

### Step 2 — Implementation decision

Ask the learner to choose a type, owner, API, or dependency.

### Step 3 — Check

Run or inspect the smallest evidence.

## Independent task, when needed

Give a bounded product task only when transfer evidence is still needed. Otherwise replace this section with a review, prediction, debugging task, or a note that the production repetition may use Pair/Ship mode.

### Acceptance criteria

- observable behavior;
- edge behavior;
- required risk-based tests;
- target platforms;
- constraints such as forbidden dependencies.

## Test and debugging plan

List checks in risk order. Identify which representative test the learner authors and which harness, fixture, or repetitive cases the coach may supply. Include at least one question requiring the learner to interpret a meaningful failure before receiving the cause.

Do not require tests of implementation details or individual modifiers. Test observable behavior, semantics, boundaries, state transitions, persistence, and failure handling in proportion to risk.

## Hint ladder

Hints should be progressively revealing.

<details>
<summary>Hint 1 — Product behavior</summary>

Restate the requirement without suggesting syntax.

</details>

<details>
<summary>Hint 2 — Ownership</summary>

Point to the responsible layer, lifetime, or type category.

</details>

<details>
<summary>Hint 3 — Shape</summary>

Offer pseudocode, a signature, or a partial skeleton.

</details>

Do not publish the complete solution until the learner has made a real attempt. When a solution is added, keep it below a clearly marked link or in a separate solution page.

## Teach-back

Use only when the explanation could expose a meaningful misconception not already resolved in the lesson. Ask the learner to explain relevant items from:

1. what problem the concept solves;
2. who owns it;
3. how long its state lives;
4. what it may depend on;
5. how it differs from the nearest React Native pattern.

## Exit ticket, when needed

One to three short questions that target unresolved mental-model risk. Omit or reduce the ticket when the same reasoning is already evidenced in the implementation, debugging, or review conversation.

## Review rubric

Identify the dimensions from [Assessment and progress](assessment-and-progress.md) that apply and any lesson-specific standard.

## Completion evidence

- [ ] Guided behavior works.
- [ ] Required mastery proof exists here or is explicitly scheduled for later retrieval.
- [ ] Independent variation works, if it supplies new evidence.
- [ ] Relevant tests pass.
- [ ] Learner explains the design.
- [ ] Review findings are resolved.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated.

## Next retrieval

Name when and how this concept will be revisited.

## References

Link only narrow, authoritative material required for the lesson.
