package com.ylevanon.alephbet.alphabet.domain

interface AlphabetRepository {
    suspend fun getLetters(): List<Letter>

    suspend fun getLetter(id: LetterId): Letter?
}
