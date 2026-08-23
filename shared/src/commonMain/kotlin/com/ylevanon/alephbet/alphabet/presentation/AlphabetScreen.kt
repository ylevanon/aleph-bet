package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.design.theme.AlephBetTheme
import com.ylevanon.alephbet.design.theme.alephBetSpacing

@Composable
internal fun AlphabetScreen(
    state: AlphabetUiState,
    onLetterClick: (LetterId) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = "Hebrew Alphabet",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.alephBetSpacing.md,
                    vertical = MaterialTheme.alephBetSpacing.sm,
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )

        when (state) {
            AlphabetUiState.Loading -> {
                CenteredStateContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    CircularProgressIndicator()
                }
            }

            is AlphabetUiState.Content -> {
                AlphabetContent(
                    letters = state.letters,
                    onLetterClick = onLetterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }

            is AlphabetUiState.Error -> {
                CenteredStateContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(MaterialTheme.alephBetSpacing.md),
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun CenteredStateContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun AlphabetContent(
    letters: List<Letter>,
    onLetterClick: (LetterId) -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier,
            contentPadding = PaddingValues(MaterialTheme.alephBetSpacing.sm),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.alephBetSpacing.sm,
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.alephBetSpacing.sm,
            ),
        ) {
            items(
                items = letters,
                key = { letter -> letter.id.value },
            ) { letter ->
                LetterCard(
                    letter = letter,
                    onClick = {
                        onLetterClick(letter.id)
                    },
                    modifier = Modifier.aspectRatio(0.6f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlphabetScreenPreview() {
    AlephBetTheme {
        AlphabetScreen(
            state = AlphabetUiState.Content(
                letters = listOf(
                    Letter(
                        id = LetterId("alef"),
                        order = 1,
                        glyph = "א",
                        pointedName = "אָלֶף",
                        latinName = "alef",
                        sounds = listOf("silent or glottal onset"),
                    ),
                    Letter(
                        id = LetterId("bet"),
                        order = 2,
                        glyph = "ב",
                        pointedName = "בֵּית",
                        latinName = "bet",
                        sounds = listOf(
                            "b with dagesh",
                            "v without dagesh",
                        ),
                    ),
                    Letter(
                        id = LetterId("gimel"),
                        order = 3,
                        glyph = "ג",
                        pointedName = "גִּימֶל",
                        latinName = "gimel",
                        sounds = listOf("g"),
                    ),
                ),
            ),
            onLetterClick = {},
        )
    }
}
