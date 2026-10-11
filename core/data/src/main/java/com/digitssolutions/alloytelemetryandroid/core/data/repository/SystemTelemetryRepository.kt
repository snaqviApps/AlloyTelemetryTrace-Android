package com.digitssolutions.alloytelemetryandroid.core.data.repository

import com.digitssolutions.alloytelemetryandroid.core.data.datasource.SystemTelemetryDataSource
import com.digitssolutions.alloytelemetryandroid.core.data.mapper.toDomain
import com.digitssolutions.alloytelemetryandroid.core.domain.model.TelemetryReadiness
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.TelemetryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 'Impl' part (implementation) of TelemetryRepository-Contract
 *
 * Mapping the data layer's TelemetryDataEntity into the domain model TelemetryReadiness
 * to fulfill the TelemetryRepository<TelemetryReadiness> contract.
 *
 * map {}
 * maps every individual emission coming out of upstream flow (Flow<TelemetryDataEntity>)
 * on-the-fly and emits the transformed result (Flow<TelemetryReadiness>) downstream.
 */
class SystemTelemetryRepository @Inject constructor (
    private val rawSystemDataSource : SystemTelemetryDataSource

) : TelemetryRepository<TelemetryReadiness> {
    override fun observeTelemetryReadiness(): Flow<TelemetryReadiness> {
        return rawSystemDataSource
            .observeRawTelemetry()
            .map { rawEntity ->
                rawEntity.toDomain()
            }
    }
}