package com.digitssolutions.alloytelemetryandroid.core.domain.model

import com.digitssolutions.alloytelemetryandroid.core.domain.AnalysisEntity
import kotlinx.serialization.Serializable

@Serializable
data class PreFlightAssessment(
    val id: String,
    val timestamp: Long,
    val overallScope: Double,
    val passFailStatus: Boolean,
    val failureReasons: List<String>
) : AnalysisEntity
