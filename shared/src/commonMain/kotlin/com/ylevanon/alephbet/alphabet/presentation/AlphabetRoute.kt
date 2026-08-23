package com.ylevanon.alephbet.alphabet.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ylevanon.alephbet.alphabet.domain.LetterId

@Composable
internal fun AlphabetRoute(
    viewModel: AlphabetViewModel,
    onLetterClick: (LetterId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AlphabetScreen(
        state = state,
        onLetterClick = onLetterClick,
        modifier = modifier,
    )
}
