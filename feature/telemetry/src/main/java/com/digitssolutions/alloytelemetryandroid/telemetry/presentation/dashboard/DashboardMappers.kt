package com.digitssolutions.alloytelemetryandroid.telemetry.presentation.dashboard

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Extension to categorize raw RSSI into human-readable quality
fun Int.toSignalQuality() : String = when {
    this >= -50 -> "Excellent"
    this >= -65 -> "Good"
    this >= -75 -> "Fair"
    else -> "Poor"
}

// Extension to map signal strength to Material 3 Colors
@Composable
fun Int.toSignalColor() : Color = when {
    this >= -50 -> MaterialTheme.colorScheme.primary
    this >= -75 -> MaterialTheme.colorScheme.tertiary
    else -> MaterialTheme.colorScheme.error
}