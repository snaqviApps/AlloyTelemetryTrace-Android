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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.viewmodel.PlaceHolderViewModel
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.Screen


/**
 * This method implements navigation-bar items
 */
@Composable
fun Navigate(viewModel: PlaceHolderViewModel) {
    val currentScreen by viewModel.backStack.collectAsState()
    val activeScreen = currentScreen.lastOrNull()

    NavigationBar() {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            selected = activeScreen is Screen.Dashboard,
            onClick = {
                viewModel.onTabClicked(Screen.Dashboard(null))
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = activeScreen is Screen.Settings,
            onClick = {
                viewModel.onTabClicked(Screen.Settings)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Movies Loader") },
            label = { Text("Movies") },
            selected = activeScreen is Screen.Explorer || activeScreen is Screen.Loading,
            onClick = {
                viewModel.onTabClicked(Screen.Explorer)
            }
        )
    }
}