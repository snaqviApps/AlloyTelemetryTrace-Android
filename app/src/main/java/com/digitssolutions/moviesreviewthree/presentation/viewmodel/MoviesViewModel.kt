package com.digitssolutions.moviesreviewthree.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitssolutions.moviesreviewthree.domain.model.Movie
import com.digitssolutions.moviesreviewthree.domain.repository.MoviesRepository
import com.digitssolutions.moviesreviewthree.presentation.MoviesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val API_KEY = "069de1d1938f6e8bd8e2096127ef987e"


@HiltViewModel
class MoviesViewModel @Inject constructor (
    private val moviesRepository: MoviesRepository<Movie>
) : ViewModel() {

    private val _moviesUIState = MutableStateFlow<MoviesUIState>(MoviesUIState.Loading(true))
    val moviesUIState = _moviesUIState.asStateFlow()

    var endPoint by mutableStateOf<String>("popular")           // default endPoint
        private set

    init {
        fetchInitialMovies(endPoint)
    }

    private fun fetchInitialMovies(defaultEndPoint: String) {
        viewModelScope.launch {
            val result : Result<List<Movie>> = moviesRepository.getMovies(
                defaultCategory = endPoint,
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