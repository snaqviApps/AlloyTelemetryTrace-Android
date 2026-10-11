package com.digitssolutions.alloytelemetryandroid.core.domain.repository

import com.digitssolutions.alloytelemetryandroid.core.domain.DomainEntity
import kotlinx.coroutines.flow.Flow

/**
 * Covariance (out)
 * TelemetryRepository<TelemetryReadiness> can be safely assigned wherever
 * a TelemetryRepository<TelemetryEntity> is expected.
 */
interface TelemetryRepository<out T: DomainEntity> {
    fun observeTelemetryReadiness() : Flow<T>
}