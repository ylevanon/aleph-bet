package com.ylevanon.alephbet.alphabet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ylevanon.alephbet.alphabet.domain.AlphabetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

internal class AlphabetViewModel(
    private val alphabetRepository: AlphabetRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AlphabetUiState>(
        AlphabetUiState.Loading,
    )

    internal val uiState: StateFlow<AlphabetUiState> = _uiState.asStateFlow()

    init {
        loadLetters()
    }

    private fun loadLetters() {
        viewModelScope.launch {
            try {
                val letters = alphabetRepository.getLetters()
                _uiState.value = AlphabetUiState.Content(letters)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (_: Exception) {
                _uiState.value = AlphabetUiState.Error(
                    message = "Unable to load the Hebrew alphabet.",
                )
            }
        }
    }
}
