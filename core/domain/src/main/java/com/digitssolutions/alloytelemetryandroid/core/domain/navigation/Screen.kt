package com.digitssolutions.alloytelemetryandroid.core.domain.navigation

import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryData

sealed interface Screen {
    data object Loading : Screen
    data class Dashboard(val telemetryData: TelemetryData?) : Screen
    data object Explorer : Screen
    data object Settings : Screen
}