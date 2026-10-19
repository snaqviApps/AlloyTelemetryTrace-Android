package com.digitssolutions.alloytelemetryandroid.core.data.datasource

import com.digitssolutions.alloytelemetryandroid.core.data.model.TelemetryDataEntity
import kotlinx.coroutines.flow.Flow

interface SystemTelemetryDataSource {
    fun observeRawTelemetry() : Flow<TelemetryDataEntity>
}