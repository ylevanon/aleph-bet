package com.ylevanon.alephbet.alphabet.domain

data class Letter(
    val id: LetterId,
    val order: Int,
    val glyph: String,
    val latinName: String,
    val sounds: List<String> = emptyList(),
)
