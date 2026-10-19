package com.digitssolutions.alloytelemetryandroid.core.domain.model.state

import kotlinx.serialization.Serializable

@Serializable
enum class DeviceThermalState {
    NORMAL,
    MODERATE,
    SEVERE,
    CRITICAL,
    UNKNOWN
}