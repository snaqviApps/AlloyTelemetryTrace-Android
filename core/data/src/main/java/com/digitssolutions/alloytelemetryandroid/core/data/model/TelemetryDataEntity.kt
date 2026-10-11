package com.digitssolutions.alloytelemetryandroid.core.data.model

data class TelemetryDataEntity(
    val rsrpDbm: Int,
    val wifiRssi: Int,
    val isCellularConnected: Boolean,
    val isWifiConnected: Boolean,
    val rawThermalStatus: Int           // Raw Android integer stays safely in the data layer
)
