package com.digitssolutions.alloytelemetryandroid.core.domain

/** Root Marker for all domain entities*/
interface DomainEntity

/** Telemetry-Marker for real-time hardware signal feeds & device metrics */
interface TelemetryEntity : DomainEntity

/** Marker for processed computed, or historical analysis results */
interface AnalysisEntity : DomainEntity

