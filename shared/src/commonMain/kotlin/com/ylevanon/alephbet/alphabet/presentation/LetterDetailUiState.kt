package com.ylevanon.alephbet.alphabet.presentation

import com.ylevanon.alephbet.alphabet.domain.Letter

internal sealed interface LetterDetailUiState {
    data object Loading : LetterDetailUiState

    data class Content(val letter: Letter) : LetterDetailUiState

    data class Error(val message: String) : LetterDetailUiState
}
