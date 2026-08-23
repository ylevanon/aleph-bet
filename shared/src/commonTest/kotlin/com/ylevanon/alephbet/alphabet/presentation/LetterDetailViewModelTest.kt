package com.ylevanon.alephbet.alphabet.presentation

import com.ylevanon.alephbet.alphabet.domain.AlphabetRepository
import com.ylevanon.alephbet.alphabet.domain.Letter
import com.ylevanon.alephbet.alphabet.domain.LetterId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LetterDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val bet = Letter(
        id = LetterId("bet"),
        order = 2,
        glyph = "ב",
        pointedName = "בֵּית",
        latinName = "bet",
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private class FakeAlphabetRepository(
        private val letters: List<Letter> = emptyList(),
        private val failure: Exception? = null,
    ) : AlphabetRepository {

        override suspend fun getLetters(): List<Letter> {
            failure?.let { exception -> throw exception }
            return letters
        }

        override suspend fun getLetter(id: LetterId): Letter? {
            return getLetters().firstOrNull { letter -> letter.id == id }
        }
    }

    @Test
    fun loadingLetterPublishesContent() = runTest(testDispatcher) {
        val viewModel = LetterDetailViewModel(
            alphabetRepository = FakeAlphabetRepository(
                letters = listOf(bet),
            ),
            letterId = LetterId("bet"),
        )

        assertEquals(
            expected = LetterDetailUiState.Loading,
            actual = viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            expected = LetterDetailUiState.Content(
                letter = bet,
            ),
            actual = viewModel.uiState.value,
        )
    }

    @Test
    fun repositoryFailurePublishesError() = runTest(testDispatcher) {
        val viewModel = LetterDetailViewModel(
            letterId = LetterId("bet"),
            alphabetRepository = FakeAlphabetRepository(
                failure = Exception("Test failure"),
            ),
        )

        assertEquals(
            expected = LetterDetailUiState.Loading,
            actual = viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            expected = LetterDetailUiState.Error(
                message = "Sorry. Could not load letter",
            ),
            actual = viewModel.uiState.value,
        )
    }

    @Test
    fun missingLetterPublishesNotFoundError() = runTest(testDispatcher) {
        val viewModel = LetterDetailViewModel(
            letterId = LetterId("missing"),
            alphabetRepository = FakeAlphabetRepository(),
        )

        assertEquals(
            expected = LetterDetailUiState.Loading,
            actual = viewModel.uiState.value,
        )

        advanceUntilIdle()

        assertEquals(
            expected = LetterDetailUiState.Error(
                message = "Letter not found.",
            ),
            actual = viewModel.uiState.value,
        )
    }
}
