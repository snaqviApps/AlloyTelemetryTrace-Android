package com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen

import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.experimentaldata.TelemetryData

sealed interface Screen {
    data object Loading : Screen
    data class Dashboard(val telemetryData: TelemetryData?) : Screen
    data object Explorer : Screen
    data object Settings : Screen
}