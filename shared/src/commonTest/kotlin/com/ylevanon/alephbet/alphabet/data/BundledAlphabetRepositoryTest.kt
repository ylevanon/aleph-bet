package com.ylevanon.alephbet.alphabet.data

import com.ylevanon.alephbet.alphabet.domain.LetterId
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class BundledAlphabetRepositoryTest {

    @Test
    fun getLettersLoadsBundledAlphabet() = runTest {
        val repository = BundledAlphabetRepository()

        val letters = repository.getLetters()

        assertEquals(
            expected = 27,
            actual = letters.size,
        )
        assertEquals(
            expected = LetterId("alef"),
            actual = letters.first().id,
        )
    }

    @Test
    fun getLetterReturnsMatchingLetter() = runTest {
        val repository = BundledAlphabetRepository()

        val bet = repository.getLetter(LetterId("bet"))

        assertNotNull(bet)
        assertEquals(
            expected = "ב",
            actual = bet.glyph,
        )
    }

    @Test
    fun getLetterReturnsNullForUnknownId() = runTest {
        val repository = BundledAlphabetRepository()

        val letter = repository.getLetter(
            LetterId("not-a-letter"),
        )

        assertNull(letter)
    }
}
