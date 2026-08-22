package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId

@Composable
fun LetterCard(
    letter: Letter,
    isSelected: Boolean,
    onClick: () -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp,
        onClick = onClick,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = letter.glyph,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = letter.latinName,
                style = MaterialTheme.typography.titleMedium,
            )

            if (letter.sounds.isNotEmpty()) {
                Text(
                    text = letter.sounds.joinToString(" or "),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onPlayAudio,
            ) {
                Text("Audio")
            }
        }
    }
}

@Preview
@Composable
private fun AlephLetterCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LetterCard(
                letter = Letter(
                    id = LetterId("aleph-preview"),
                    order = 1,
                    glyph = "א",
                    latinName = "Aleph",
                ),
                isSelected = false,
                onClick = {},
                onPlayAudio = {},
            )
        }
    }
}
