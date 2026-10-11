package com.digitssolutions.alloytelemetryandroid.core.data.mapper

import com.digitssolutions.alloytelemetryandroid.core.data.model.TelemetryDataEntity
import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness
import com.digitssolutions.alloytelemetryandroid.core.domain.model.state.DeviceThermalState

fun TelemetryDataEntity.toDomain() : TelemetryReadiness {
    val domainThermalStatue: DeviceThermalState = when(rawThermalStatus) {
        0, 1 -> DeviceThermalState.NORMAL           // THERMAL_STATUS_NONE, THERMAL_STATUS_LIGHT
        2    -> DeviceThermalState.MODERATE         // THERMAL_STATUS_MODERATE
        3    -> DeviceThermalState.SEVERE           // THERMAL_STATUS_SEVERE
        4, 5, 6 -> DeviceThermalState.CRITICAL      // CRITICAL, EMERGENCY, SHUTDOWN
        else -> DeviceThermalState.UNKNOWN
    }

    return TelemetryReadiness(
        rsrpDbm = this.rsrpDbm,
        wifiRssi = this.wifiRssi,
        isCellularConnected = this.isCellularConnected,
        isWifiConnected = this.isWifiConnected,
        rawThermalStatus = domainThermalStatue
    )
}