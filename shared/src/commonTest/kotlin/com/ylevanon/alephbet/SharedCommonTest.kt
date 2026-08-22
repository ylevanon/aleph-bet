package com.ylevanon.alephbet

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlin.test.Test

class SharedCommonTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun composeUiHarnessFindsVisibleText() = runComposeUiTest {
        setContent {
            Text("Harness ready")
        }

        onNodeWithText("Harness ready").assertTextEquals("Harness ready")
    }
}
