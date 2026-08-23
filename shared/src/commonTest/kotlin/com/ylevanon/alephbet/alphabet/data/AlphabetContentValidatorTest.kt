package com.ylevanon.alephbet.alphabet.data

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AlphabetContentValidatorTest {

    @Test
    fun acceptsCanonicalAlphabetContent() = runTest {
        val content = decodeAlphabetContent(readBundledAlphabetJson())

        assertEquals(content, validateAlphabetContent(content))
    }

    @Test
    fun rejectsDuplicateLetterIds() = runTest {
        val content = decodeAlphabetContent(readBundledAlphabetJson())
        val duplicateAlefContent = content.copy(
            letters = content.letters + content.letters.first(),
        )

        val failure = assertFailsWith<AlphabetContentValidationException> {
            validateAlphabetContent(duplicateAlefContent)
        }

        assertContains(failure.message.orEmpty(), "alef")
    }

    @Test
    fun rejectsMissingFinalFormBaseLetter() = runTest {
        val json = readBundledAlphabetJson()
        val content = decodeAlphabetContent(json)
        val missingBaseId = "missing_kaf"
        val invalidContent = content.copy(
            letters = content.letters.map { letter ->
                if (letter.id != "final_kaf") {
                    letter
                } else {
                    letter.copy(baseLetterId = missingBaseId)
                }
            },
        )

        val failure = assertFailsWith<AlphabetContentValidationException> {
            validateAlphabetContent(invalidContent)
        }

        assertContains(failure.message.orEmpty(), "final_kaf")
        assertContains(failure.message.orEmpty(), missingBaseId)
    }
}
