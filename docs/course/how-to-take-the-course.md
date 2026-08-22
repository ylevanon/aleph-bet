# How to take the course

Status: Active operating agreement

Last updated: 2026-08-22

## The short answer

The public repository contains a complete 59-lesson **map** and 24 fully written lesson pages. Module 00 is passed, and complete lesson packs are available through Module 04.

That is intentional, but it should have been stated more clearly. The course uses a rolling publication model so later explanations and exercises can respond to actual evidence instead of guessing the learner's Kotlin level months in advance.

The learner does **not** need to edit the public Markdown files or paste every answer into a worksheet. The normal classroom is the conversation. The normal lab is the private application repository. The public documentation is the textbook, roadmap, and sanitized progress record.

The learner also does **not** need to type every production line. The course requires direct implementation when the work supplies new mastery evidence. Repetition, configuration, bulk content, exhaustive test expansion, and routine polish may move to paired or coach implementation after the pattern is understood. See [Teaching method and coaching contract](method.md#own-the-decisions-not-every-keystroke).

All published lesson pages inherit this operating agreement. If an older page says “independent task” or lists a broad completion checklist without an explicit work split, the coach must first decide whether the item supplies new mastery evidence. The governing method overrides repetitive template ceremony.

## What came from the referenced masterclass

The exact [React Native and Supabase Masterclass Notion board](https://notjust.notion.site/React-Native-Supabase-Masterclass-47a69a60bc464c399b5a0df4d3c4a630), its linked lesson pages, and the [eight-hour video](https://www.youtube.com/watch?v=rIYzLhkG9TA) were used as teaching-format references.

They influenced:

- the visible module and status structure;
- one real application serving as the course narrative;
- short objectives at the beginning of a lesson;
- sequential implementation steps and focused examples;
- assignments appearing before solutions;
- treating production and release as part of the curriculum;
- maintaining written material that can be revisited away from the video.

They did not supply:

- the Aleph Bet product requirements;
- the Kotlin, KMP, Compose, Room, or Koin curriculum;
- the 59-lesson sequence;
- the architecture;
- the first baseline lesson;
- copied prose, source code, or exercises.

Lesson 00.01 is an original diagnostic and course-calibration lesson. The published Module 00 and Module 01 lessons are also application-specific KMP instruction, not Kotlin rewrites of food-ordering lessons.

## The three working surfaces

| Surface | Purpose | Normal editor | Public? |
|---|---|---|---|
| This conversation | Questions, predictions, explanations, hints, teach-backs, and review | Learner and coach | No |
| Aleph Bet application repository | Kotlin source, tests, experiments, and lesson commits | Learner for mastery-critical work; learner and coach for production completion | No |
| `aleph-bet-docs` | Lesson text, syllabus, architecture decisions, and sanitized status | Coach | Yes |
| Optional personal notes | Scratch explanations or a learning journal | Learner | Learner's choice |

Raw answers, uncertain first attempts, and private application code are not copied into the public documentation. A public progress update should record only a status and a short, non-sensitive evidence summary.

## Which chat or session to use

Use the same chat for all lessons in the current module. Do not create a new session for every lesson.

The existing conversation contains useful teaching evidence: earlier answers, recurring misconceptions, preferred hint level, architecture decisions, and what has already been reviewed. Keeping a module together makes the coaching more coherent.

A new chat is reasonable at a module boundary or when the current conversation becomes difficult to navigate. Before switching, the coach should update and push the public progress record with:

- the last passed lesson;
- current mastery evidence;
- unresolved questions or concepts to revisit;
- the next lesson to start;
- links to the relevant course and architecture documents.

The new chat can then begin with:

> Continue the Aleph Bet KMP course. Read `docs/course/README.md`, `docs/course/how-to-take-the-course.md`, and `docs/course/assessment-and-progress.md`, then start lesson XX.XX.

This makes the repository the durable handoff between sessions. A fresh chat should not be expected to reconstruct every nuance of the previous conversation without reading that handoff.

Use a separate chat immediately when the task is genuinely unrelated to the course. For example, marketing work or an unrelated application should not interrupt the lesson thread.

## How full lessons are published

The curriculum has two layers.

### Layer 1: stable course map

The [syllabus](syllabus.md) names all 59 lessons, their intended learning outcomes, and their product artifacts. It makes the complete journey inspectable from the beginning.

### Layer 2: full lesson pages

Full pages contain the actual retrieval questions, explanations, labs, acceptance criteria, hints, tests, teach-back prompts, and exit tickets. These are published in a rolling window.

The default publication policy is:

1. Lesson 00.01 is published alone because its answers calibrate the course.
2. After that baseline, the coach publishes the remainder of Module 00 and the next complete module, adjusted to the evidence.
3. Before the learner starts any later module, all full lesson pages for that module are available.
4. The coach keeps at least one complete module ahead whenever practical.
5. Lesson outcomes remain stable, but examples, pacing, and optional remediation can change.

The learner therefore does not have to pass every individual lesson before the next page exists. The first diagnostic is the special case. Nor is the learner locked out of the map: every planned lesson is already visible in the syllabus.

Before a new module starts live, its complete lesson pack must be published. Pages may adapt later, but the coach should not invent the module's delivery sequence one chat message at a time while the learner is implementing it.

If reading ahead is more useful than adaptation at any point, the learner can say:

> Publish the next module now.

## What happens in a live lesson

Starting a lesson does not mean silently completing the whole Markdown page.

1. The learner says `Start lesson 00.01`.
2. The coach asks the first small group of questions from the lesson.
3. The learner answers naturally in the conversation. Short, uncertain, or partial answers are valid starting points.
4. The coach responds to the reasoning, teaches the next idea, and continues one section at a time.
5. When a coding lab begins, the coach identifies whether the task is in Learn, Pair, or Ship mode.
6. In Learn mode, the learner edits the private application files and says when the representative attempt is ready for review.
7. The coach inspects the relevant diff, runs appropriate checks, and explains findings without silently replacing mastery-critical work.
8. Independent variation and teach-back are used only when they provide evidence not already demonstrated.
9. Repetitive or mechanical completion may then move to Pair or Ship mode, with the learner reviewing the result.
10. When the applicable product and learning evidence meets the lesson criteria, the lesson is marked passed and the public progress record is updated, committed, and pushed.

The learner can paste an error, describe an idea, attach a screenshot, point to a file, or simply say “I am stuck.” There is no required answer syntax.

For a little structure, an optional response format is:

```text
Answer:
Confidence: low / medium / high
Reasoning:
Question or uncertainty:
```

## How hints and solutions work

The learner controls how much help is revealed.

- `Give me a nudge` asks for a focusing question.
- `Hint level 3` points to the relevant mental model or reference.
- `Give me a skeleton` supplies structure with meaningful gaps.
- `Pair with me` works through decisions one at a time.
- `Show me the solution` is available after a real attempt. A follow-up explanation or transfer task is required only if mastery evidence is still missing.
- `Pair with me on the integration` keeps the learner responsible for decisions while the coach supplies structure or glue code.
- `Ship this part` asks the coach to implement repetitive or production-focused work. This does not invalidate skills already demonstrated.
- `Take over and implement this` is also available. The coach records a deferred learning step only when the task was the remaining evidence for a new core skill.

Compiler errors and failing tests are lesson material, not evidence that the learner has failed.

## How much code and testing the learner owns

Use this default split:

| Work | Default mode |
|---|---|
| First real use of a new Kotlin/KMP/Compose concept | Learn |
| Domain rules, state transitions, state ownership, schema, migrations | Learn or Pair |
| First representative test for an important risk | Learn |
| Test harnesses, fixtures, configuration, and equivalent test rows | Pair or Ship |
| Repeated modifier/component patterns | Ship after proof |
| Bulk reviewed content and resource wiring | Pair or Ship |
| Mechanical refactors, formatting, store metadata, and routine polish | Ship |

The learner must remain able to review and modify shipped work on the release path. The goal is ownership of behavior and architecture, not ownership of every keystroke.

## What to do while reading at work

The public docs are safe to read without the private application source. While away from the development machine, the learner can:

- read the current lesson and architecture references;
- write a prediction or teach-back in the conversation;
- ask conceptual Kotlin or KMP questions;
- request the next module's lesson pack;
- note what should be tried later in the private repository.

Implementation evidence can wait until the learner is back at the private repository. There is no need to put answers or source code into public Markdown just to continue the conversation.

## Starting now

Say:

> Start lesson 00.01.

The coach will begin with the first diagnostic section and proceed interactively rather than asking for the entire lesson in one response.
