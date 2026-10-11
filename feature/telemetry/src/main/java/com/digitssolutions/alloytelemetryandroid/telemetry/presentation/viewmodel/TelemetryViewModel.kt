package com.digitssolutions.alloytelemetryandroid.telemetry.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.TelemetryRepository
import com.digitssolutions.alloytelemetryandroid.telemetry.presentation.TelemetryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class TelemetryViewModel @Inject constructor(
    private var telemetryRepository: TelemetryRepository<TelemetryReadiness>
) : ViewModel() {

    val uiState : StateFlow<TelemetryUiState> = telemetryRepository
        .observeTelemetryReadiness()
        .map<TelemetryReadiness, TelemetryUiState> { readiness ->        // Step A: Transform Data Model to UI State Model
            TelemetryUiState.Success(readiness)
        }
        .catch { throwable ->            // Step B: Catch upstream errors gracefully
            emit(TelemetryUiState.Error(throwable.localizedMessage ?: "Unknown Telemetry Error"))
        }
        .stateIn(                                                        // Step C: .steteIn() Convert the Cold Flow into a Hot StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TelemetryUiState.Loading(true)
        )

}