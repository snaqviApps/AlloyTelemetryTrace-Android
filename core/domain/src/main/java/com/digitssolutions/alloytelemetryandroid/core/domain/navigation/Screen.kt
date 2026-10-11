package com.digitssolutions.alloytelemetryandroid.core.domain.navigation

import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness

sealed interface Screen {
    data object Loading : Screen
    data class Dashboard(val telemetryReadiness: TelemetryReadiness?) : Screen
    data object Explorer : Screen
    data object Settings : Screen
}