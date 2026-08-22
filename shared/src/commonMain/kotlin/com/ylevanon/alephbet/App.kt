package com.ylevanon.alephbet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.alphabet.presentation.LetterCard

private fun formatAlphabetProgress(introducedLetterCount: Int, baseLetterCount: Int): String {
    if (introducedLetterCount == baseLetterCount) {
        return "Alphabet complete"
    } else {
        return "$introducedLetterCount of $baseLetterCount letters introduced"
    }
}

@Composable
@Preview
fun App() {
    val screenTitle: String = "Learn the Hebrew alphabet"
    var selectedLetterId: LetterId? by remember { mutableStateOf(null) }
    val baseLetterCount = 22
    val introducedLetterCount = 4
    val progressText = formatAlphabetProgress(
        introducedLetterCount = introducedLetterCount,
        baseLetterCount = baseLetterCount,
    )

    val aleph = Letter(
        id = LetterId("aleph"),
        order = 1,
        glyph = "א",
        latinName = "Aleph",
        sounds = listOf("a"),
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
    val dalet = Letter(
        id = LetterId("dalet"),
        order = 4,
        glyph = "ד",
        latinName = "Dalet",
        sounds = listOf("d"),
    )

    val letters = listOf(aleph, gimel, bet, dalet).sortedBy { it.order }

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val buttonLabel = if (showContent) "Hide letters" else "Start learning"
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = screenTitle,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = progressText,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Button(onClick = { selectedLetterId = null }) {
                Text("Clear selection")
            }

            Button(onClick = { showContent = !showContent }) {
                Text(buttonLabel)
            }

            AnimatedVisibility(showContent) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Rtl,
                ) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(
                            items = letters,
                            key = { letter -> letter.id.value },
                        ) { letter ->
                            LetterCard(
                                letter = letter,
                                isSelected = letter.id == selectedLetterId,
                                onClick = {
                                    selectedLetterId = letter.id
                                },
                                onPlayAudio = {
                                    println("Playing audio for ${letter.id.value}")
                                },
                                modifier = Modifier.aspectRatio(0.6f),
                            )
                        }
                    }
                }
            }
        }
    }
}
