# Absolute-beginner Hebrew research: source and search log

Research run: 2026-08-10 (America/Los_Angeles)  
Eligibility window: public material dated 2021-08-10 through 2026-08-10. The focal 2018 YouTube video was inspected under the stated exception; only comments inside the eligibility window were recorded.

## Final sample

- 95 distinct first-person learner accounts.
- Platform mix: 46 Reddit, 35 YouTube comments, 13 Apple App Store reviews, and 1 public Hebrew forum account.
- 47 distinct source URLs. The largest single URL, the focal YouTube video, contributes 17/95 accounts; the largest platform, Reddit, contributes 46/95. Neither a single URL nor a platform is a majority.
- Dates run from 2021-10-12 to 2026-08-06.
- The CSV has one row per learner, not one row per statement. One Reddit duplicate and repeated follow-ups by the same B-Hebrew learner were merged/replaced during deduplication.
- Usernames and other unnecessary personal details were not recorded. Adult status is explicit where a source says so, inferred only from adult context (work, partner, payment, relocation, graduate study, adult religious education), or marked age-unknown where the public post gives no minor cue. Explicit minors were excluded.

## Search and collection log

All searches were run on 2026-08-10. Search-result snippets were opened when needed to recover full dates and context. Public YouTube comments were requested newest-first and capped per video; no login-only material was used.

| Search family / query | Platforms examined | What it contributed | Important exclusions or gaps |
|---|---|---|---|
| `site:reddit.com/r/hebrew beginner Hebrew alphabet can't read learning Duolingo` | Reddit | Zero-start accounts; alphabet-to-course transition; Duolingo limitations | Native-only advice excluded unless the commenter also described their own learner experience |
| `site:reddit.com/r/hebrew "absolute beginner" Hebrew learning` | Reddit | Starting paths and resource selection | Generic recommendation lists without first-person use excluded |
| `site:reddit.com/r/languagelearning Hebrew beginner alphabet` | Reddit | Relationship motivation and initial sequencing | Pre-2021-08-10 threads excluded |
| `site:reddit.com Hebrew learning partner boyfriend girlfriend beginner` | Reddit | Partner/relationship motivation | Very few qualifying recent accounts found; this remains a thin segment |
| `site:reddit.com/r/hebrew learning Hebrew "can't read"` and alphabet/year variants | Reddit | Decoding, similar letters, print/cursive, long stalls | Explicit minors excluded from the final set |
| `site:reddit.com/r/hebrew Hebrew "stopped" learning` / `"gave up" Duolingo` | Reddit | Abandonment, stopping, restarting | Posts about other languages excluded |
| `site:reddit.com/r/hebrew beginner Hebrew partner spouse ...`, `heritage family`, `conversion Judaism`, `Israel travel move aliyah` | Reddit | Motivation segments: conversion/prayer, heritage, family, travel, work, relocation | Motivations not stated by the learner remain `not stated`; advice was not converted into motivation evidence |
| `site:reddit.com/r/hebrew beginner Hebrew niqqud struggle reading`, cursive, listening, grammar, vocabulary | Reddit | Detailed problem categories and stage transitions | Intermediate-only accounts excluded unless they explicitly described a zero/near-zero starting barrier or functional illiteracy |
| `Hebrew learning app reviews beginner alphabet app store review` and App Store-specific variants | Apple App Store | Drops, Hebrew by Nemo, Learn Hebrew Alphabet & Words, BNR, Mango reviews | Developer copy and developer replies excluded as learner behavior; pre-cutoff reviews excluded |
| `Hebrew language forum beginner learning alphabet 2022`; B-Hebrew-specific searches | B-Hebrew / forums | A dated Biblical-oriented beginner account using the focal video | Most forum hits were teacher answers, old threads, or the same learner’s follow-ups; only one distinct qualifying learner remained |
| `"_UU6Fe7lqIo" Hebrew alphabet comments` | YouTube plus indexed web | Located focal video and related forum use | The video itself is older than cutoff and is not learner evidence |
| Newest 80 public comments from `_UU6Fe7lqIo` | YouTube | 17 dated learner accounts: script confusion, print/cursive, restart after seven years, pacing, missing continuation, motivation, accessibility | Praise-only, spam, politics-only, harassment, and comments with no learner behavior were excluded |
| YouTube search `learn Hebrew alphabet beginner` | YouTube | Located `Ekc3lIVdHEU`, `uiGXh2BFKUo`, `tk1njVL723w`, `jcb-xAvJKqE` | Videos with only promotional or non-learner comments did not contribute |
| Newest 40 comments per additional alphabet video | YouTube | 18 accounts across four videos: religion/theology, adult class, slow learning, vowels, similar sounds, classroom accountability, paid-course failure, conversion, work | Comments were included only when first-person starting level, motivation, problem, resource use, or outcome was present |
| `site:apps.apple.com ... Hebrew ... Ratings & Reviews` | App Store | Pricing/paywall, review pacing, micro-sessions, course ceilings | General-language reviews that did not explicitly concern Hebrew were excluded |

## Inclusion rules applied

An account qualified when all of the following held:

1. The public item or comment was dated 2021-08-10 or later.
2. It was first-person learner evidence, not merely a teacher, developer, marketer, or observer claim.
3. The learner was at zero/near-zero Hebrew, unable to read, newly learning the script, or describing a barrier that began at that stage. A small number of later-stage accounts were retained only when they explicitly traced a zero-start pathway or remained functionally illiterate.
4. The account contained at least one useful field: motivation, goal, resource tried, cadence, reported problem, stop/restart behavior, or learner-reported outcome.
5. No explicit evidence showed the learner was a minor. Age-unknown accounts are labeled rather than silently treated as confirmed adults.

The sample distinguishes Modern, Biblical, liturgical/prayer, mixed, and script-only/unspecified learning in the `hebrew_type` column. An account can have more than one atomic tag, but each tag is counted at most once for that learner.

## Classification and counting rules

- **Observed problem:** the learner explicitly reports confusion, friction, failure, stopping, or a mismatch. There are 83/95 such rows.
- **Suspected problem:** reserved for an inferred difficulty. No frequency claim uses an inferred problem as though it were observed. A few motivations are explicitly marked inferred, and those are not used in strict motivation counts.
- **Recommended solution:** advice or a proposed next action without evidence that this learner tried it. 23/95 rows.
- **Tried solution:** learner reports use, but no concrete skill or persistence gain. 54/95 rows.
- **Supported solution:** learner reports a concrete improvement, such as fewer errors, expanded vocabulary, improved pronunciation, successful beginner-text reading, or sustained use for months. 18/95 rows.

Counts in the reports use 95 as the full-sample denominator unless a segment-specific denominator is printed. Problem counts include only rows classified `Observed problem`. A problem tag is counted once per learner even if the excerpt mentions it repeatedly. Grouped top-level categories are unions, so a learner contributes at most once to a grouped category; grouped categories overlap with one another.

## Saturation check

The first 75 rows established these top-level categories:

1. Access/product/content-format friction.
2. Script acquisition (letter recognition, similar letters, print/cursive, transliteration).
3. Progression to usable Hebrew (vocabulary, grammar, listening, speaking, transfer).
4. Orientation/resource/segment navigation.
5. Continuity, confidence, and practice.
6. Niqqud and unpointed decoding.

Rows E076–E095 are the required 20-account post-threshold check. They added examples of paywalls, missing next lessons, inconsistent audio, accountability, reading transfer, course trust, niqqud, conversion, morphology, transliteration, live-class discomfort, feedback, micro-sessions, and slow liturgical decoding. Every one mapped to one or more of the six existing categories; none required a seventh top-level problem category. Saturation was therefore reached at 95 accounts, below the 150-account cap.

## Exclusions

- Material before 2021-08-10, except inspection of the focal video itself.
- Comments on the focal video older than the cutoff.
- Explicit minors; a prior draft’s two minor accounts were removed rather than counted.
- Native-speaker advice without a first-person learner history.
- Teacher, tutor, course creator, or developer claims about what students do.
- SEO pages, promotional landing pages, app descriptions, and developer replies as evidence of learner behavior.
- Praise-only comments, emoji-only comments, spam, political statements without learning behavior, and resource recommendations with no personal use.
- Intermediate/advanced accounts with no zero-start or literacy relevance.
- General-language app reviews that did not identify Hebrew.
- Duplicate comments or follow-ups attributable to the same learner.

## Known gaps and biases

- Self-selection favors people experiencing friction or strong enthusiasm; it is not a prevalence survey of all Hebrew learners.
- Reddit supplies 46/95 accounts and therefore shapes the language and questions in the sample, although it is not a majority and findings are marked cross-platform only when at least two platform types support them.
- App Store evidence overrepresents people motivated enough to post a review, and YouTube comments overrepresent video learners. Outcomes are self-reported and generally short-term.
- Only 1/95 account comes from a traditional Hebrew forum after deduplication. Recent public forum evidence was sparse; many search hits were old or teacher-led.
- Adult status is often not disclosed. The analysis is strongest for the explicit/implied-adult contexts and weaker for age-unknown accounts.
- Study cadence is usually absent or only a single-session snapshot. This limits conclusions about ten-minutes-per-week learners.
- Relationship-motivated, shy/live-lesson-avoidant, and very-low-frequency learners are present but small subgroups; the focal girlfriend should not be treated as representative.
- Public comments cannot verify actual proficiency, completion, or whether reported routines continued after posting.
