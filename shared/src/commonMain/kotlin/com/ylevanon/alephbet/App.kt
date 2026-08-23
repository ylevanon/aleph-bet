package com.ylevanon.alephbet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ylevanon.alephbet.alphabet.data.BundledAlphabetRepository
import com.ylevanon.alephbet.alphabet.presentation.AlphabetRoute
import com.ylevanon.alephbet.alphabet.presentation.AlphabetViewModel
import com.ylevanon.alephbet.design.theme.AlephBetTheme
import kotlinx.serialization.Serializable

@Serializable
internal data object AlphabetDemoDestination

@Serializable
internal data object DetailDemoDestination

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val alphabetViewModel = viewModel {
        AlphabetViewModel(
            alphabetRepository = BundledAlphabetRepository(),
        )
    }

    AlephBetTheme {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AlphabetDemoDestination,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable<AlphabetDemoDestination> {
                    AlphabetRoute(
                        viewModel = alphabetViewModel,
                        onLetterClick = {
                            navController.navigate(DetailDemoDestination)
                        },
                    )
                }

                composable<DetailDemoDestination> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Temporary detail destination")
                        Button(
                            onClick = {
                                navController.popBackStack()
                            },
                        ) {
                            Text(text = "Back to alphabet")
                        }
                    }
                }
            }
        }
    }
}
