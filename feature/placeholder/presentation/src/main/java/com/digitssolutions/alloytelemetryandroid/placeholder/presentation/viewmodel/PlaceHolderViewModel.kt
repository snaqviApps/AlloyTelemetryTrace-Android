package com.digitssolutions.alloytelemetryandroid.placeholder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitssolutions.alloytelemetryandroid.core.domain.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.MoviesRepository
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.MoviesUIState
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val API_KEY = "069de1d1938f6e8bd8e2096127ef987e"

@HiltViewModel
class PlaceHolderViewModel @Inject constructor (
    private val moviesRepository: MoviesRepository<DataPlaceHolderInstance>
) : ViewModel() {
    private val _moviesUIState = MutableStateFlow<MoviesUIState>(MoviesUIState.Loading(true))
    val moviesUIState = _moviesUIState.asStateFlow()

    private val _endPoint =  MutableStateFlow<String>("popular")                             // Default endPoint
    val endPoint = _endPoint.asStateFlow()


    // Navigation Stack:
    private val _backStack = MutableStateFlow<List<Screen>>(listOf(Screen.Loading))
    val backStack = _backStack.asStateFlow()

    init {
        fetchMovies(endPoint.value)
    }

    private fun fetchMovies(defaultEndPoint: String) {
        viewModelScope.launch {
            val result : Result<List<DataPlaceHolderInstance>> = moviesRepository.getMovies(
                defaultCategory = defaultEndPoint,
                apiKey = API_KEY,
                page = 5
            )
            result.onSuccess { moviesList->
                _moviesUIState.update {
                    MoviesUIState.Success(moviesList, defaultEndPoint)
                }
            }
            result.onFailure {
                _moviesUIState.value = MoviesUIState.Error("${it.message}")
            }
            }
        }

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