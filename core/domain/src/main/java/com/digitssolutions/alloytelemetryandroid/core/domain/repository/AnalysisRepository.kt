package com.digitssolutions.alloytelemetryandroid.core.domain.repository

import com.digitssolutions.alloytelemetryandroid.core.domain.DomainEntity

/**
 * Analytics repository bound exclusively to AnalysisEntity types
 */
interface AnalysisRepository<out T: DomainEntity> {
    fun analyzeFlightSession(sessionId: String) : Result<T>
}