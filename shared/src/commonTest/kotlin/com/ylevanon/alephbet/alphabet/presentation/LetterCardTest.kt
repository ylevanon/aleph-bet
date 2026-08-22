package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import kotlin.test.Test
import kotlin.test.assertTrue

class LetterCardTest {

    private val bet = Letter(
        id = LetterId("bet-test"),
        order = 2,
        glyph = "ב",
        latinName = "Bet",
        sounds = listOf("b", "v"),
    )

    private val aleph = Letter(
        id = LetterId("aleph-test"),
        order = 1,
        glyph = "א",
        latinName = "Aleph",
    )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyLetterCardRenders() = runComposeUiTest {
        setContent {
            MaterialTheme {
                LetterCard(
                    letter = bet,
                    isSelected = false,
                    onClick = {},
                    onPlayAudio = {},
                )
            }
        }

        onNodeWithText(
            "Bet",
            useUnmergedTree = true,
        ).assertTextEquals("Bet")

        onNodeWithText(
            "ב",
            useUnmergedTree = true,
        ).assertTextEquals("ב")

        onNodeWithText(
            "b or v",
            useUnmergedTree = true,
        ).assertTextEquals("b or v")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyLetterCardWithoutSoundsRenders() = runComposeUiTest {
        setContent {
            MaterialTheme {
                LetterCard(
                    letter = aleph,
                    isSelected = false,
                    onClick = {},
                    onPlayAudio = {},
                )
            }
        }

        onNodeWithText("Aleph")
            .assertTextEquals("א", "Aleph")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickingCardCallsOnClick() = runComposeUiTest {
        var wasClicked = false

        setContent {
            MaterialTheme {
                LetterCard(
                    letter = bet,
                    isSelected = false,
                    onClick = {
                        wasClicked = true
                    },
                    onPlayAudio = {},
                )
            }
        }

        onNodeWithText("Bet")
            .performClick()

        assertTrue(wasClicked)
    }
}
