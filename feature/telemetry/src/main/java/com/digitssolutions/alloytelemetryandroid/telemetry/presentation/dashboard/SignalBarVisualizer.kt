package com.digitssolutions.alloytelemetryandroid.telemetry.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignalBarVisualizer(
    rssi: Int,
    modifier: Modifier = Modifier
) {
    val qualityText = rssi.toSignalQuality()
    val indicatorColor = rssi.toSignalColor()

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for(i in 1..4) {
            val isActive = when (i) {
                1 -> rssi >= -85
                2 -> rssi >= -75
                3 -> rssi >= -65
                4 -> rssi >= -50
                else -> false
            }
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height((i * 8).dp)     // Graduated bar heights
                    .background(
                        color = if(isActive) indicatorColor else MaterialTheme.colorScheme.outlineVariant,
                        shape = MaterialTheme.shapes.extraSmall
                    )
            )
        }
        Text(
            text = "$rssi dBm ($qualityText)",
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SignalBarVisualizerPreview() {
    SignalBarVisualizer(
        rssi = 9,
        modifier = Modifier
    )
}