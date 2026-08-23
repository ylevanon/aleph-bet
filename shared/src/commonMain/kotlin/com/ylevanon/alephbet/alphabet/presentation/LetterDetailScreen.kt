package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.design.theme.AlephBetTheme
import com.ylevanon.alephbet.design.theme.alephBetHebrewFontFamily
import com.ylevanon.alephbet.design.theme.alephBetSpacing

@Composable
internal fun LetterDetailScreen(
    state: LetterDetailUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.alephBetSpacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = spacing.md,
                vertical = spacing.sm,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextButton(onClick = onBackClick) {
            Text(text = "←")
        }

        Spacer(modifier = Modifier.height(spacing.lg))
        when (state) {
            LetterDetailUiState.Loading -> {
                CenteredStateContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    CircularProgressIndicator()
                }
            }

            is LetterDetailUiState.Content -> {
                LetterDetailContent(
                    letter = state.letter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }

            is LetterDetailUiState.Error -> {
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
private fun LetterDetailContent(
    letter: Letter,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.alephBetSpacing
    val hebrewFontFamily = alephBetHebrewFontFamily()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.sm),
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl,
            ) {
                Text(
                    text = letter.glyph,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = hebrewFontFamily,
                        fontSize = 112.sp,
                        lineHeight = 128.sp,
                    ),
                )

                Text(
                    text = letter.pointedName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = hebrewFontFamily,
                    ),
                )
            }

            Text(
                text = letter.latinName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        if (letter.sounds.isNotEmpty()) {
            Spacer(modifier = Modifier.height(spacing.xl))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(spacing.md))

            DetailSection(title = "Sounds") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.sm),
                ) {
                    letter.sounds.forEach { sound ->
                        Text(
                            text = sound,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }

        if (letter.forms.isNotEmpty()) {
            Spacer(modifier = Modifier.height(spacing.lg))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(spacing.md))

            DetailSection(title = "Alternate forms") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = spacing.lg,
                        alignment = Alignment.CenterHorizontally,
                    ),
                ) {
                    letter.forms.forEach { form ->
                        CompositionLocalProvider(
                            LocalLayoutDirection provides LayoutDirection.Rtl,
                        ) {
                            Text(
                                text = form,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontFamily = hebrewFontFamily,
                                ),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.lg))
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit,
) {
    val spacing = MaterialTheme.alephBetSpacing

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        content()
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

@Preview
@Composable
private fun LetterDetailScreenPreview() {
    AlephBetTheme {
        LetterDetailScreen(
            onBackClick = {},
            state = LetterDetailUiState.Content(
                letter = Letter(
                    id = LetterId("bet"),
                    order = 2,
                    glyph = "ב",
                    pointedName = "בֵּית",
                    latinName = "bet",
                    sounds = listOf(
                        "b with dagesh",
                        "v without dagesh",
                    ),
                    forms = listOf("בּ", "ב"),
                )
            )
        )
    }
}
