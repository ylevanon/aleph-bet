# Lesson 00.03 — The build-run-debug loop

Status: Ready after Lesson 00.02

Module: 00 — Orientation and toolchain

Estimated focused time: 60–90 minutes; first iOS dependency resolution may add setup time

## Product outcome

The generated shared starter renders on an Android emulator and an iOS simulator. You can run the smallest relevant build, read the first useful compiler error, and verify a fix without changing unrelated code.

## Learning outcomes

By the end, you can:

- select the correct run configuration or Gradle task for a target;
- distinguish Gradle sync, compilation, packaging, installation, and application runtime;
- reduce a long failure report to the first relevant cause;
- use logs, breakpoints, and a deliberately introduced compiler error as evidence;
- maintain a short build ledger for asynchronous learning sessions.

## Prerequisites

- Lesson 00.02 passed.
- Its authoritative project map is available.
- An Android virtual device and an iOS simulator are installed.

## Retrieval warm-up

1. Where do you verify which Gradle modules exist?
2. What is the difference between `iosMain` and `iosApp`?
3. Where would a pure `Letter` model and an Android-only audio implementation compile?

## Why the app needs this now

Every later lesson depends on a trustworthy feedback loop. If a test, preview, emulator, and simulator all fail for different reasons, adding application code only increases ambiguity. We first establish a known-good baseline and a repeatable way to isolate failures.

## Mental model

“Build the app” hides several stages:

```text
Gradle sync
    -> resolve configuration and dependencies
    -> compile Kotlin for a selected target
    -> package/link the platform application
    -> install and launch it
    -> observe runtime behavior and logs
```

A failure belongs to one stage. Diagnose the stage before proposing the fix.

Use this loop:

```text
Reproduce -> read first relevant cause -> form one hypothesis
          -> run the smallest discriminating check -> change one thing
          -> rerun the same check -> record the result
```

## React Native bridge

| Familiar pattern | KMP equivalent | Important difference |
|---|---|---|
| Metro starts successfully | Gradle sync/configuration succeeds | Sync does not prove either native app compiles or launches. |
| TypeScript/Babel compile error | Kotlin compiler error | The error may be specific to a target compilation or source set. |
| Xcode/Gradle native build under RN | Xcode/Gradle native build here | Shared Kotlin is compiled into each native application's dependency graph. |
| Fast Refresh and console logs | Compose/app rerun, debugger, platform logs | The exact iteration mechanism varies by target and IDE configuration. |

## Vocabulary

| Term | Meaning in this lesson |
|---|---|
| Sync | Gradle evaluates project configuration and resolves the model used by the IDE. |
| Task | A named Gradle unit of work. |
| Compilation | Turning source for a target into lower-level output. |
| Packaging/linking | Producing the installable Android app or linked iOS application. |
| Run configuration | IDE instructions selecting module, task/scheme, device, and launch behavior. |
| Breakpoint | A source location where the debugger pauses execution. |
| Stack trace | Ordered call/error context; useful only after locating the relevant cause. |

## Predict before running

Order these checks from narrowest to broadest and explain the tradeoff:

- run one common test;
- compile the shared module;
- assemble the Android debug app;
- launch Android;
- launch iOS.

Then predict: if a syntax error is placed in `commonMain`, which target launches should fail? What if it is placed only in `androidMain`?

## Guided lab

### Step 1 — Record the environment, not every installed tool

Create a small session ledger:

```text
IDE and version:
JDK selected by the IDE:
Xcode version:
Android device/API:
iOS simulator/device:
Generated project date:
```

Use the IDE's KMP environment/preflight checks if available. Record warnings; do not update unrelated tools merely because a newer version exists.

### Step 2 — Discover tasks from the generated project

Use the Gradle wrapper and the module names recorded in 00.02. List tasks before copying a command from the internet.

Identify:

- one verification task;
- one shared compilation or test task;
- the Android debug assembly task;
- the generated Android and iOS run configurations.

Exact task paths belong in your project map. This lesson intentionally does not guess whether your shared module is named `shared` or `composeApp`.

### Step 3 — Establish the Android baseline

1. Start an Android virtual device.
2. Select the generated Android application run configuration.
3. Run the untouched starter.
4. Record whether the failure, if any, occurred during configuration, compilation, packaging, installation, or runtime.
5. Capture the visible starter text and one relevant log line.

Expected result: shared Compose UI is visible inside the Android application.

### Step 4 — Establish the iOS baseline

1. Ensure Xcode has finished its first-run setup and a simulator exists.
2. Select the generated iOS run configuration and simulator.
3. Run the untouched starter.
4. If signing is requested for a simulator, verify the selected configuration before changing signing settings.
5. Capture the same visible shared text and one relevant runtime observation.

Expected result: the same shared Compose root is hosted by the iOS application. Native window chrome may differ; shared behavior should match.

### Step 5 — Read a deliberate compiler error

In the smallest generated shared Kotlin file, make one reversible syntax/type mistake—for example, assign a `String` to an explicitly declared `Int`. Type the line yourself.

Before rebuilding, predict:

- which compilation will report it;
- the filename and line the compiler should identify;
- whether either application can launch from a clean rebuild.

Run the narrowest relevant check. Copy only:

```text
Task/operation:
First error that names our file:
What the compiler expected:
What it received:
My proposed correction:
```

Correct the line manually and rerun the identical check. Do not “fix” it by discarding the whole file.

### Step 6 — Use a breakpoint once

Place a breakpoint in code reached while creating the shared root UI. Launch one target in debug mode and confirm:

- execution pauses in the expected file;
- you can inspect at least one local value;
- continuing execution renders the app.

This is a tool-orientation exercise, not a Compose lifecycle lesson. Do not infer recomposition rules from one breakpoint hit.

## Independent task

Choose one generated common test or add the smallest possible `kotlin.test` smoke test. Introduce a failing expectation, predict the failure output, run only that test, correct it, and then rerun both platform applications.

### Acceptance criteria

- Android and iOS both render the shared starter after the correction.
- The narrow test fails for the predicted reason before it passes.
- The build ledger identifies the stage and first relevant cause rather than pasting an entire log.
- No dependency versions are changed without evidence that compatibility is the failure.
- Existing docs and assets remain intact.

## Test and debugging plan

Use this escalation order:

1. Reproduce the smallest known failure.
2. Read the first error tied to project source or configuration.
3. Verify filename, line, task, and target.
4. State one falsifiable hypothesis.
5. Rerun the narrowest check.
6. Expand to the affected app.
7. Verify both targets only after the narrow check passes.

If an error contains dozens of later failures, explain why the first causal error is more useful than the final summary line.

## Hint ladder

<details>
<summary>Hint 1 — Find the stage</summary>

Ask whether Gradle was configuring, Kotlin was compiling, the platform was packaging/installing, or the launched process crashed.

</details>

<details>
<summary>Hint 2 — Find our evidence</summary>

Search upward from the final failure for the first message naming a project file, module, target, or unresolved dependency.

</details>

<details>
<summary>Hint 3 — Reduce the check</summary>

If a common test fails, rerun that test or its source-set test task before rebuilding both applications.

</details>

## Teach-back

Take one failure from the lesson and explain its stage, evidence, rejected alternatives, smallest check, and proof of correction.

## Exit ticket

1. Why does a successful Gradle sync not prove the iOS app launches?
2. What makes a compiler error “first relevant”?
3. If only `androidMain` fails, what evidence would stop you from changing iOS code?
4. Why should the verification command remain the same before and after a fix?
5. What belongs in a build ledger for a session resumed tomorrow?

## Review rubric

This lesson assesses cross-platform verification, debugging discipline, source-set reasoning, and tool independence. Passing requires successful runs on both targets and a correctly diagnosed failure.

## Completion evidence

- [ ] Android starter runs.
- [ ] iOS starter runs.
- [ ] Deliberate shared compiler failure is predicted, diagnosed, and fixed.
- [ ] Independent test failure is predicted, diagnosed, and fixed.
- [ ] One debugger breakpoint is used successfully.
- [ ] Build ledger is complete.
- [ ] Lesson commit exists in the private application history.
- [ ] Progress record is updated and Module 00 gate is reviewed.

## Next retrieval

Lesson 01.01 uses the working `commonTest` loop for direct Kotlin practice. Every later lesson should continue choosing the smallest useful check before launching both apps.

## References

- [Kotlin Multiplatform quickstart and run configurations](https://kotlinlang.org/docs/multiplatform/quickstart.html)
- [Create and run a mobile Multiplatform application](https://kotlinlang.org/docs/multiplatform/multiplatform-create-first-app.html)
- [Gradle command-line basics](https://docs.gradle.org/current/userguide/command_line_interface_basics.html)
