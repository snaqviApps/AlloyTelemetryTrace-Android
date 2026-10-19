package com.digitssolutions.alloytelemetryandroid.telemetry.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.TelemetryUiState
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.viewmodel.TelemetryViewModel

@Composable
fun DashboardScreen() {
    val telemetryViewModel : TelemetryViewModel = hiltViewModel()
    val state by telemetryViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        when(val uiState = state) {
           is TelemetryUiState.Loading -> {}
            is TelemetryUiState.Success -> {
                Text(
                    text =  uiState.readiness.wifiRssi.toString(),
                    modifier = Modifier.fillMaxSize()
                        .padding(32.dp),
                    textAlign = TextAlign.Center

                )
            }
            is TelemetryUiState.Error -> {
                Text("Error loading Telemetry Data")
            }
        }
    }
}
