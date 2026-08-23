package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun LetterDetailRoute(
    viewModel: LetterDetailViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LetterDetailScreen(
        state = state,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
