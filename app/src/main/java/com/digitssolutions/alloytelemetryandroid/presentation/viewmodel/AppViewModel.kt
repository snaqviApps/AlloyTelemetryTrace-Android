package com.digitssolutions.alloytelemetryandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.digitssolutions.alloytelemetryandroid.core.domain.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class AppNavigationViewModel @Inject constructor() : ViewModel()  {

    // Navigation Stack:
    private val _backStack = MutableStateFlow<List<Screen>>(listOf(Screen.Dashboard(null)))
    val backStack = _backStack.asStateFlow()


    // Prevent 2nd click from take effect
    fun onTabClicked(screen: Screen) {
        val currentTag = _backStack.value.lastOrNull()

        // Check if we are already on this screen type
        val isAlreadyActive = when(screen) {
            is Screen.Dashboard -> currentTag is Screen.Dashboard
            is Screen.Settings -> currentTag is Screen.Settings
            is Screen.Explorer -> currentTag is Screen.Explorer
            else -> false
        }

        // 2. If already active, ignore the click (prevents re-triggering heavy lifting)
        if(isAlreadyActive) { return }

        // 3. Otherwise, perform navigation (and any associated heavy lifting)
        navigateTo(screen)
    }


    /**
     * Creating and Managing Back Stack
     */
    fun navigateTo(screenId: Screen) {
        _backStack.update { backStack ->
            backStack + screenId
        }
    }

}