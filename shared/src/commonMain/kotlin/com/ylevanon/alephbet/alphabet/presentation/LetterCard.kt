package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import com.ylevanon.alephbet.design.theme.AlephBetTheme
import com.ylevanon.alephbet.design.theme.alephBetHebrewFontFamily
import com.ylevanon.alephbet.design.theme.alephBetPalette
import com.ylevanon.alephbet.design.theme.alephBetSpacing

@Composable
fun LetterCard(
    letter: Letter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hebrewFontFamily = alephBetHebrewFontFamily()

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        color = MaterialTheme.alephBetPalette.surfaceRaised,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.alephBetPalette.line,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.alephBetSpacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.alephBetSpacing.xs),
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl,
            ) {
                Text(
                    text = letter.glyph,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = hebrewFontFamily,
                        lineHeight = 56.sp,
                    ),
                )
            }

            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr,
            ) {
                Text(
                    text = letter.latinName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlephLetterCardPreview() {
    AlephBetTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LetterCard(
                letter = Letter(
                    id = LetterId("aleph-preview"),
                    order = 1,
                    glyph = "א",
                    pointedName = "אָלֶף",
                    latinName = "Aleph",
                ),
                onClick = {},
            )
        }
    }
}
