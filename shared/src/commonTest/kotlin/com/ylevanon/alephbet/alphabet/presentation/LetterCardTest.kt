package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onAllNodesWithText
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
        pointedName = "בֵּית",
        latinName = "Bet",
        sounds = listOf("b", "v"),
    )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyLetterCardRenders() = runComposeUiTest {
        setContent {
            MaterialTheme {
                LetterCard(
                    letter = bet,
                    onClick = {},
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

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun soundDescriptionsAreNotShown() = runComposeUiTest {
        setContent {
            MaterialTheme {
                LetterCard(
                    letter = bet,
                    onClick = {},
                )
            }
        }

        onAllNodesWithText("b or v")
            .assertCountEquals(0)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickingCardCallsOnClick() = runComposeUiTest {
        var wasClicked = false

        setContent {
            MaterialTheme {
                LetterCard(
                    letter = bet,
                    onClick = {
                        wasClicked = true
                    },
                )
            }
        }

        onNodeWithText("Bet")
            .performClick()

        assertTrue(wasClicked)
    }
}
