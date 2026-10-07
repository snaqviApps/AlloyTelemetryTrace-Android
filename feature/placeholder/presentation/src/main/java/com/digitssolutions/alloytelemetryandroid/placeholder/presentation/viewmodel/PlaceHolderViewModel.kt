package com.digitssolutions.alloytelemetryandroid.placeholder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitssolutions.alloytelemetryandroid.core.domain.model.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.MoviesRepository
import com.digitssolutions.alloytelemetryandroid.placeholder.presentation.MoviesUIState
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



}