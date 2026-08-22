package com.ylevanon.alephbet.alphabet.data

import kotlinx.serialization.json.Json

internal fun decodeAlphabetContent(json: String): AlphabetContentDto {
    val content: AlphabetContentDto = Json.decodeFromString(json)
    return content
}