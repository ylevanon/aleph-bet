package com.ylevanon.alephbet.alphabet.data

import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.alphabet.domain.LetterSoundSample
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AlphabetMapperTest {

    private val expectedBet = Letter(
        id = LetterId("bet"),
        order = 2,
        glyph = "ב",
        pointedName = "בֵּית",
        latinName = "bet",
        sounds = listOf(
            "b with dagesh",
            "v without dagesh",
        ),
        forms = listOf("בּ", "ב"),
        soundSamples = listOf(
            LetterSoundSample(
                id = "b",
                pointed = "בָּ",
            ),
            LetterSoundSample(
                id = "v",
                pointed = "בָ",
            ),
        ),
    )

    @Test
    fun mapsCanonicalBetToDomain() = runTest {
        val json = readBundledAlphabetJson()
        val content = decodeAlphabetContent(json)
        val letters = content.toDomainLetters()

        val bet = letters.single { letter ->
            letter.id == LetterId("bet")
        }

        assertEquals(expectedBet, bet)
    }
}
