package com.digitssolutions.alloytelemetryandroid.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digitssolutions.alloytelemetryandroid.core.domain.navigation.Screen
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.MoviesUIState
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.DashboardScreen
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.MovieCard
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.MovieErrorCard
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.SettingsScreen
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.viewmodel.PlaceHolderViewModel
import com.digitssolutions.alloytelemetryandroid.presentation.util.Navigate
import com.digitssolutions.alloytelemetryandroid.presentation.viewmodel.AppNavigationViewModel
import com.digitssolutions.alloytelemetryandroid.ui.theme.AlloyTelemetryAndroidTheme


@Composable
fun InitialSetup(
    innerPadding: PaddingValues,
    viewModel: AppNavigationViewModel = hiltViewModel()
) {
    val currentScreen by viewModel.backStack.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = innerPadding.calculateTopPadding() + 12.dp),
                text = "This is Top Bar",
                textAlign = TextAlign.Center
            )
        },
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        bottomBar = {
            val activeScreen = currentScreen.lastOrNull()
            Navigate(
                activeScreen = activeScreen,
                onTabSelected = viewModel::onTabClicked
            )

        },
        content = { rootInnerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(rootInnerPadding),     // 👈 applies topBar/bottomBar padding
                color = MaterialTheme.colorScheme.background
            ) {
                when (currentScreen.lastOrNull()) {
                    is Screen.Dashboard -> DashboardScreen()
                    is Screen.Settings -> SettingsScreen()
                    is Screen.Loading, is Screen.Explorer -> {

                        /** 👈 PlaceHolderViewModel is ONLY created when this tab is active, memory-economical */
                        val viewModel: PlaceHolderViewModel = hiltViewModel()
                        val moviesUIState by viewModel.moviesUIState.collectAsStateWithLifecycle()
                        when (val mState = moviesUIState) {
                            is MoviesUIState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(rootInnerPadding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(width = 120.dp, height = 70.dp),
                                        strokeWidth = 4.dp
                                    )
                                }
                            }

                            is MoviesUIState.Error -> {
                                MovieErrorCard(
                                    innerPadding = rootInnerPadding,
                                    error = mState.error
                                )
                            }

                            is MoviesUIState.Success -> {
                                MovieCard(
                                    innerPadding = PaddingValues(0.dp), // 👈 Surface already padded the parent
                                    dataPlaceHolderInstanceList = mState.dataPlaceHolderInstance
                                )
                            }
                        }
                    }
                    null -> {}
                }
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
fun MoviesLandPreview() {
    AlloyTelemetryAndroidTheme() {
        InitialSetup(
            innerPadding = PaddingValues()
        )
    }
}