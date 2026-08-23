package com.ylevanon.alephbet.alphabet.data

import com.ylevanon.alephbet.alphabet.domain.AlphabetRepository
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId

internal class BundledAlphabetRepository : AlphabetRepository {

    override suspend fun getLetter(id: LetterId): Letter? {
        return this.getLetters()
            .firstOrNull { letter -> letter.id == id }
    }

    override suspend fun getLetters(): List<Letter> {
        val json = readBundledAlphabetJson()
        val content = decodeAlphabetContent(json)
        return content.toDomainLetters()
    }
}