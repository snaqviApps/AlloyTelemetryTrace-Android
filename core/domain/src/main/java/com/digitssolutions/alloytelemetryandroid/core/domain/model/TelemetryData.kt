package com.digitssolutions.alloytelemetryandroid.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TelemetryData(
    val timestamp: Long,
    val cpuUsage: Double,
    val memoryUsage: Double,
    val networkThroughput: Double,
    val latency: Double
)

@Serializable
data class TraceData(
    val id: String,
    val name: String,
    val startTime: Long,
    val duration: Long,
    val tags: Map<String, String>
)
