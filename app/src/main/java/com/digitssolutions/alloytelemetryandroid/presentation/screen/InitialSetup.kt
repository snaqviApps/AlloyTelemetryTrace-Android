package com.digitssolutions.alloytelemetryandroid.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter
import androidx.media3.extractor.text.webvtt.WebvttCssStyle
import com.digitssolutions.alloytelemetryandroid.core.domain.navigation.Screen
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.MoviesUIState
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.MovieCard
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.MovieErrorCard
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.SettingsScreen
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.viewmodel.PlaceHolderViewModel
import com.digitssolutions.alloytelemetryandroid.presentation.util.Navigate
import com.digitssolutions.alloytelemetryandroid.presentation.viewmodel.AppNavigationViewModel
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.dashboard.DashboardScreen
import com.digitssolutions.alloytelemetryandroid.ui.theme.AlloyTelemetryAndroidTheme

@Composable
fun InitialSetup(
    innerPadding: PaddingValues,
    appNavViewModel: AppNavigationViewModel = hiltViewModel()
) {
    val currentScreen by appNavViewModel.backStack.collectAsStateWithLifecycle()
    val activeScreen = currentScreen.lastOrNull()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            val headerTitle = when (activeScreen) {
                is Screen.Dashboard -> "System Health Check"
                is Screen.Explorer -> "PlaceHoder List: Movies"
                is Screen.Settings -> "Settings"
                else -> "Alloy Telemetry Trace"
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = headerTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0x926D6D7C) // Or your preferred top bar color
//                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        bottomBar = {
            Navigate(
                activeScreen = activeScreen,
                onTabSelected = appNavViewModel::onTabClicked
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
                                    innerPadding = PaddingValues(2.dp), // 👈 Surface already padded the parent
                                    moviesAsPlaceHolder = mState.placeHolderEntity
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

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MoviesLandPreview() {
    AlloyTelemetryAndroidTheme() {
        InitialSetup(
            innerPadding = PaddingValues(),
            viewModel()
        )
    }
}