package com.ylevanon.alephbet.alphabet.data

import kotlinx.serialization.Serializable


@Serializable
internal data class SoundSampleDto(
    val id: String,
    val pointed: String,
)

@Serializable
internal data class LetterDto(
    val order: Int,
    val id: String,
    val glyph: String,
    val namePointed: String,
    val nameLatin: String,
    val finalForm: Boolean,
    val sounds: List<String>,
    val soundSamples: List<SoundSampleDto> = emptyList(),
    val baseLetterId: String? = null,
    val forms: List<String> = emptyList(),
)


@Serializable
internal data class AlphabetContentDto(
    val schemaVersion: Int,
    val locale: String,
    val scriptStyle: String,
    val editorialStatus: String,
    val transliterationKey: Map<String, String>,
    val letters: List<LetterDto>,
)
