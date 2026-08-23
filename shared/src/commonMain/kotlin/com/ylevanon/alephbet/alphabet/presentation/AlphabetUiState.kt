package com.ylevanon.alephbet.alphabet.presentation

import com.ylevanon.alephbet.alphabet.domain.Letter

internal sealed interface AlphabetUiState {
    data object Loading : AlphabetUiState

    data class Content(val letters: List<Letter>) : AlphabetUiState

    data class Error(val message: String) : AlphabetUiState
}
