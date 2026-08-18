# Aleph Bet V1 Product Spec

Status: Draft for implementation  
Last updated: 2026-08-10

## Product summary

Aleph Bet is a polished, local-only Hebrew learning app for absolute beginners. V1 teaches the complete block-letter Aleph-Bet, essential niqqud, and approximately 100 useful beginner words through excellent explanations, high-fidelity Israeli Hebrew audio, strong visuals, quizzes, repetition, and lightweight progress tracking.

The app uses familiar language-learning utility rather than depending on a novel feature wedge. Its quality should come from content accuracy, audio, visual taste, clarity, responsiveness, and the overall learning experience.

## Why we are building it

The initial learner is an English-speaking adult learning Hebrew for her relationship with a Hebrew speaker and a possible future family. She has essentially no foundation, does not currently want lessons, and may engage for anything from a few seconds to several minutes at a time.

The project is successful if it becomes genuinely useful to her. Broader distribution, market validation, and monetization are separate later questions. It is also a practical Kotlin Multiplatform and Compose Multiplatform learning project for the developer.

## V1 learning promise

After completing the V1 curriculum, a learner should be able to:

- Recognize all 22 Hebrew block letters and the five final forms.
- Recall the letters' names and common Modern Hebrew sounds.
- Understand important sound distinctions such as bet/vet, kaf/khaf, pe/fe, and shin/sin.
- Recognize and pronounce the essential niqqud used to teach beginner reading.
- Sound out beginner words presented with niqqud.
- Begin recognizing learned words without niqqud.
- Understand and recognize approximately 100 useful beginner words.
- Hear clear, accurate Israeli Hebrew pronunciation and compare it with their own.

V1 does not promise conversational fluency, grammar mastery, or comprehension of unrestricted Hebrew text.

## Product principles

### Excellent content is the product

Explanations must be accurate, concise, friendly, and written for someone with no Hebrew foundation. V1 should avoid both unexplained complexity and misleading simplification.

### Audio should feel like a treat

Sound is a hero interaction. Every important letter, niqqud sound, syllable, and word should have polished audio, responsive playback, and an attractive synchronized visualization.

### Start small or continue

The app should not require a fixed lesson commitment. A learner can complete one useful interaction and stop or continue into a longer learning session. Progress is saved locally.

### Block Hebrew only

V1 teaches printed block Hebrew. It does not teach handwriting, tracing, or cursive Hebrew.

### Local-only by design

The shipped app requires no account, backend, or network connection. Curriculum, audio, progress, and recordings remain on the device.

## Curriculum scope

### Aleph-Bet

- All 22 standard block letters.
- Five final forms.
- Letter names and common Modern Hebrew sounds.
- Relevant dagesh and shin/sin distinctions.
- Similar-looking and similar-sounding letters explained where useful.
- Right-to-left reading orientation.

### Niqqud

- The essential vowel marks needed for beginner decoding.
- Letter-and-vowel combinations with audio.
- Pointed reading practice.
- A deliberate introduction to the fact that ordinary Modern Hebrew is usually unpointed.

This is a practical Modern Hebrew treatment, not an exhaustive academic study of every cantillation or historical pronunciation rule.

### Vocabulary

- Approximately 100 curated beginner words.
- Selection informed by frequency, usefulness, teachability, emotional relevance, and curriculum coverage.
- Each word includes pointed Hebrew, unpointed Hebrew, meaning, transliteration where helpful, explanation, and audio.
- Words should exercise the letters and niqqud introduced in the curriculum.
- Pure corpus frequency is not the only selection criterion; grammatical particles and difficult forms should be included only when they are appropriate for an absolute beginner.

## Core product areas

### Learn

A guided curriculum introducing letters, sounds, niqqud, decoding, and vocabulary in a deliberate sequence.

### Review

Lightweight adaptive repetition that gives more attention to material the learner misses and less to material consistently recalled.

### Explore

A browsable reference for unlocked letters, niqqud, and words. Learners can revisit explanations and replay audio freely.

### Progress

A simple view of letter, niqqud, and vocabulary progress, plus a lightweight streak and small mastery milestones.

## Learning interactions

V1 may use a focused set of reusable interaction types:

- See a letter and identify its name or sound.
- Hear a sound and select the correct letter.
- Match a niqqud mark to its sound.
- Combine a block letter and niqqud into a syllable.
- Sound out a pointed word.
- Recognize a learned word without niqqud.
- Hear a word and identify it.
- Match a word to its meaning.

The experience should feel varied without requiring a large collection of unrelated minigames.

## Audio and pronunciation experience

- Gemini-TTS is the preferred generation source, with alternatives evaluated if Hebrew quality is insufficient.
- Audio is generated during content production and bundled with the app; V1 does not synthesize speech at runtime.
- A consistent, warm Israeli Hebrew voice should be selected through auditions.
- Each generated clip must be reviewed by a Hebrew speaker before shipping.
- Important words should offer natural and slower teaching playback when useful.
- Playback should animate a waveform and pitch/intonation visualization.
- The learner can record themselves locally, replay their attempt, and compare it with the reference.
- V1 does not provide an automated pronunciation score or claim that waveform similarity proves correct pronunciation.
- Learner recordings never leave the device.

## Progress and motivation

- Progress is stored locally.
- Track mastery separately for letters, niqqud, and words.
- Include a simple streak or return indicator without making it the center of the experience.
- Use restrained milestones rather than noisy or excessive gamification.
- Do not use hearts, energy limits, forced waiting, or punishment mechanics.

## Visual direction

- Adult-oriented, warm, modern, and calm.
- Hebrew typography must be exceptionally clear, including niqqud placement.
- Right-to-left content must render correctly in every state.
- Audio visualization should feel expressive and premium rather than scientific or clinical.
- Motion should support comprehension and delight without slowing down repeated use.
- Avoid childish mascots, generic classroom styling, and visual clutter.

## V1 business and privacy model

- No account.
- No backend.
- No cloud synchronization.
- No advertising.
- No tracking SDKs.
- No subscription or payment flow.
- Fully usable offline after installation.

Competitor free-versus-paid behavior will be documented for product context but does not need to shape the initial personal V1.

## Explicit non-goals

- Handwriting, tracing, or cursive Hebrew.
- Full grammar curriculum.
- Conversational courses or open-ended dialogue.
- Speech recognition or automated pronunciation grading.
- AI tutoring inside the shipped app.
- Social features or leaderboards.
- Cross-device synchronization.
- Thousands of vocabulary words.
- Biblical Hebrew, cantillation, or a prayer-focused curriculum.
- Market validation or monetization testing.

## Source material and originality

The curriculum may draw factual and pedagogical inspiration from:

- The HebrewPod101 video, *Learn ALL Hebrew Alphabet in 40 Minutes*.
- The current Hebrew-learning App Store landscape.
- The absolute-beginner learner research in `docs/research/`.
- Frequency resources used to inform vocabulary selection.
- Native Hebrew editorial review.

The app will use original wording, visuals, lesson structure, and audio. It will not copy or redistribute third-party video, slides, narration, or proprietary app assets.

## Competitive context

The primary established reference is *Learn Hebrew Alphabet & Words*, which currently has 566 US App Store ratings. Recent entrants provide examples of alphabet drills, spaced repetition, vocabulary, audio, streaks, progress, and offline learning, but generally have few visible ratings.

Competitor research is intended to inventory available features and interaction patterns, not prove what works or manufacture a differentiation claim.

## Delivery approach

The intended V1 delivery window is less than one month. Kotlin and Compose Multiplatform learning are expected to be the main implementation constraint. If the schedule becomes tight, reduce peripheral interactions before compromising content accuracy, audio quality, or the core learning experience.

Before producing the entire curriculum, create a vertical slice containing a few letters, initial niqqud, several words, polished audio, visualization, recording, quizzes, and local progress. Use it to verify the experience and asset workflow, then scale the same system to the complete V1 content set.

## Remaining product decisions

- Exact letter and niqqud teaching sequence.
- Final list and organization of approximately 100 words.
- Selected Gemini voice and audio direction.
- Visual identity, typography, and motion language.
- Exact review behavior and streak rules.
- Which interactions are required for the first vertical slice.

These decisions should be captured as focused content and design specifications without expanding V1 into a general Hebrew course.
