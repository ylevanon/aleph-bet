package com.ylevanon.alephbet.alphabet.data

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class AlphabetContentDecoderTest {

    private val expectedAlef = LetterDto(
        order = 1,
        id = "alef",
        glyph = "א",
        namePointed = "אָלֶף",
        nameLatin = "alef",
        finalForm = false,
        sounds = listOf("silent or glottal onset"),
        soundSamples = listOf(
            SoundSampleDto(
                id = "a",
                pointed = "אָ",
            ),
        ),
    )

    @Test
    fun decodesBundledAlphabetContent() = runTest {
        val json = readBundledAlphabetJson()
        val content = decodeAlphabetContent(json)

        assertContains(content.letters, expectedAlef)
        assertEquals(27, content.letters.size)
    }

    @Test
    fun verifyLetterIdsUnique() = runTest {
        val json = readBundledAlphabetJson()
        val content = decodeAlphabetContent(json)
        val uniqueLetterIdSet: Set<String> = content.letters.map(LetterDto::id).toSet()
        assertEquals(content.letters.size, uniqueLetterIdSet.size)
    }
}
