package com.ylevanon.alephbet.alphabet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ylevanon.alephbet.alphabet.domain.AlphabetRepository
import com.ylevanon.alephbet.alphabet.domain.LetterId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

internal class LetterDetailViewModel(
    private val letterId: LetterId,
    private val alphabetRepository: AlphabetRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LetterDetailUiState>(
        LetterDetailUiState.Loading,
    )

    internal val uiState: StateFlow<LetterDetailUiState> = _uiState.asStateFlow()

    init {
        loadLetter()
    }

    private fun loadLetter() {
        viewModelScope.launch {
            try {
                val letter = alphabetRepository.getLetter(letterId)
                _uiState.value = if (letter != null) {
                    LetterDetailUiState.Content(letter)
                } else {
                    LetterDetailUiState.Error(
                        message = "Letter not found.",
                    )
                }
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (_: Exception) {
                _uiState.value = LetterDetailUiState.Error(
                    message = "Sorry. Could not load letter",
                )
            }
        }
    }
}
