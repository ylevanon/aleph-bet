package com.ylevanon.alephbet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import alephbet.shared.generated.resources.Res
import alephbet.shared.generated.resources.compose_multiplatform
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId


private fun formatAlphabetProgress(introducedLetterCount: Int, baseLetterCount: Int): String {
    if (introducedLetterCount == baseLetterCount) {
       return "Alphabet complete"
    } else {
       return "$introducedLetterCount of $baseLetterCount letters introduced"
    }
}

private fun formatLetterLabel(letter: Letter): String =
    if (letter.sounds.isEmpty()) {
        "${letter.glyph} — ${letter.latinName}"
    } else {
        "${letter.glyph} — ${letter.latinName} (${letter.sounds.joinToString(" or ")})"
    }

@Composable
@Preview
fun App() {
    val screenTitle: String = "Learn the Hebrew alphabet"

    val baseLetterCount = 22
    val introducedLetterCount = 3
    val progressText = formatAlphabetProgress(
        introducedLetterCount = introducedLetterCount,
        baseLetterCount = baseLetterCount,
    )

    val aleph = Letter(
        id = LetterId("aleph"),
        order = 1,
        glyph = "א",
        latinName = "Aleph",
    )
    val bet = Letter(
        id = LetterId("bet"),
        order = 2,
        glyph = "ב",
        latinName = "Bet",
        sounds = listOf("b", "v"),
    )

    val gimel = Letter(
        id = LetterId("gimel"),
        order = 3,
        glyph = "ג",
        latinName = "Gimel",
        sounds = listOf("g"),
    )

    val letters = listOf(aleph, gimel, bet).sortedBy { it.order }

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val buttonLabel = if (showContent) "Hide greeting" else "Start learning"
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(screenTitle, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(progressText)
            letters.forEach { letter ->
                Text(
                    text = formatLetterLabel(letter),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Button(onClick = { showContent = !showContent }) {
                Text(buttonLabel)
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}
