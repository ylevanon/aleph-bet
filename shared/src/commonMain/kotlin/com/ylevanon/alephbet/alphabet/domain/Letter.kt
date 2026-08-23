package com.ylevanon.alephbet.alphabet.domain

data class Letter(
    val id: LetterId,
    val order: Int,
    val glyph: String,
    val pointedName: String,
    val latinName: String,
    val sounds: List<String> = emptyList(),
    val forms: List<String> = emptyList(),
    val soundSamples: List<LetterSoundSample> = emptyList(),
    val isFinalForm: Boolean = false,
    val baseLetterId: LetterId? = null,
)
