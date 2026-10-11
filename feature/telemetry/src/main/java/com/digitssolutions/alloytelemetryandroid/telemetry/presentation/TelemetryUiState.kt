package com.digitssolutions.alloytelemetryandroid.telemetry.presentation

import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness

sealed interface TelemetryUiState {
    data class Loading(val isLoading: Boolean = false) : TelemetryUiState
    data class Success(val readiness : TelemetryReadiness) : TelemetryUiState
    data class Error(val eMessage : String) : TelemetryUiState
}