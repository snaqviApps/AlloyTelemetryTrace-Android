package com.digitssolutions.alloytelemetryandroid.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.digitssolutions.alloytelemetryandroid.core.domain.navigation.Screen

/**
 * This method implements navigation-bar items
 */
@Composable
fun Navigate(
    activeScreen: Screen?,
    onTabSelected : (Screen) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = activeScreen is Screen.Dashboard,
            onClick = { onTabSelected(Screen.Dashboard(null)) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = activeScreen is Screen.Settings,
            onClick = { onTabSelected(Screen.Settings) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Movies Loader") },
            label = { Text("Movies") },
            selected = activeScreen is Screen.Explorer || activeScreen is Screen.Loading,
            onClick = { onTabSelected(Screen.Explorer) }
        )
    }
}