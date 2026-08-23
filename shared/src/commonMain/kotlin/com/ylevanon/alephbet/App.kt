package com.ylevanon.alephbet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ylevanon.alephbet.alphabet.data.BundledAlphabetRepository
import com.ylevanon.alephbet.alphabet.presentation.AlphabetRoute
import com.ylevanon.alephbet.alphabet.presentation.AlphabetViewModel
import com.ylevanon.alephbet.design.theme.AlephBetTheme

@Composable
@Preview
fun App() {
    val alphabetViewModel = viewModel {
        AlphabetViewModel(
            alphabetRepository = BundledAlphabetRepository(),
        )
    }

    AlephBetTheme {
        Scaffold { innerPadding ->
            AlphabetRoute(
                modifier = Modifier.padding(innerPadding),
                viewModel = alphabetViewModel,
                onLetterClick = {},
            )
        }
    }
}
