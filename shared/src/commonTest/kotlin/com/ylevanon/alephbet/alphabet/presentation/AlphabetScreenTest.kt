package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.design.theme.AlephBetTheme
import kotlin.test.Test
import kotlin.test.assertEquals

class AlphabetScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickingLetterEmitsItsId() = runComposeUiTest {
        val bet = Letter(
            id = LetterId("bet"),
            order = 2,
            glyph = "ב",
            pointedName = "בֵּית",
            latinName = "bet",
        )
        var clickedLetterId: LetterId? = null

        setContent {
            AlephBetTheme {
                AlphabetScreen(
                    state = AlphabetUiState.Content(
                        letters = listOf(bet),
                    ),
                    onLetterClick = { letterId ->
                        clickedLetterId = letterId
                    },
                )
            }
        }

        onNodeWithText("bet")
            .performClick()

        assertEquals(
            expected = LetterId("bet"),
            actual = clickedLetterId,
        )
    }
}
