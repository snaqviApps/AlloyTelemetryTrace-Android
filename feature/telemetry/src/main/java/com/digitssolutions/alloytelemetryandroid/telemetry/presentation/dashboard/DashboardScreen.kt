package com.digitssolutions.alloytelemetryandroid.telemetry.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.TelemetryUiState
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.viewmodel.TelemetryViewModel

@Composable
fun DashboardScreen() {
    val telemetryViewModel: TelemetryViewModel = hiltViewModel()
    val state by telemetryViewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        color = MaterialTheme.colorScheme.background,
        content = {
            when (val uiState = state) {
                is TelemetryUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TelemetryUiState.Success -> {
                    val readiness = uiState.readiness

                    // Hierarchical layout scope container
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        // 2. Wi-Fi Signal Visualizer Card
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Wi-Fi Signal Strength",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                SignalBarVisualizer(rssi = readiness.wifiRssi)
                            }
                        }

                        // Additional metrics row using RowScope layout
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MetricInfoCard(
                                title = "Stream Status",
                                value = "Active",
                                modifier = Modifier.weight(1f) // <--- RowScope child modifier in action!
                            )
                            MetricInfoCard(
                                title = "Buffer Health",
                                value = "Optimal",
                                modifier = Modifier.weight(1f) // <--- RowScope child modifier in action!
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MetricInfoCard(
                                title = "Thermal Conditions",
                                value = uiState.readiness.rawThermalStatus.toString(),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                }

                is TelemetryUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error loading Telemetry Data",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )

                    }
                }
            }
        }
    )

}

@Composable
fun MetricInfoCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
            Text(text = value, style = MaterialTheme.typography.bodySmall)
        }
    }
}
