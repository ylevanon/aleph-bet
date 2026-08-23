package com.ylevanon.alephbet

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.test.waitUntilAtLeastOneExists
import kotlin.test.Test

class AppTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun appDisplaysBundledAlphabet() = runComposeUiTest {
        setContent {
            App()
        }

        waitUntilAtLeastOneExists(
            matcher = hasText("dalet"),
            timeoutMillis = 5_000,
        )

        onNodeWithText(
            "dalet",
            useUnmergedTree = true,
        ).assertTextEquals("dalet")
    }
}
