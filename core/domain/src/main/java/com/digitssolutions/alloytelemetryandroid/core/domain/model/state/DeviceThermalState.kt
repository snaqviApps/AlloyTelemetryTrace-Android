package com.digitssolutions.alloytelemetryandroid.core.domain.model.state

import kotlinx.serialization.Serializable

@Serializable
enum class DeviceThermalState {
    NONE,
    NORMAL,
    LIGHT,
    MODERATE,
    SEVERE,
    CRITICAL,
    UNKNOWN
}