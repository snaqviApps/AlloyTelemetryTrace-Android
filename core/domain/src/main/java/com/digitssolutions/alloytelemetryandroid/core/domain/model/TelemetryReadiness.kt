package com.digitssolutions.alloytelemetryandroid.core.domain.model

import com.digitssolutions.alloytelemetryandroid.core.domain.TelemetryEntity
import com.digitssolutions.alloytelemetryandroid.core.domain.model.state.DeviceThermalState
import kotlinx.serialization.Serializable

@Serializable
data class TelemetryReadiness(
    val rsrpDbm: Int,
    val wifiRssi: Int,
    val isCellularConnected: Boolean,
    val isWifiConnected: Boolean,
    val rawThermalStatus: DeviceThermalState
) : TelemetryEntity