package com.ylevanon.alephbet

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        state = rememberWindowState(
            width = 390.dp,
            height = 844.dp
        ),
        onCloseRequest = ::exitApplication,
        title = "AlephBet",
    ) {
        App()
    }
}
