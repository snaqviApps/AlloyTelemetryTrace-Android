package com.digitssolutions.moviesreviewthree.presentation

import com.digitssolutions.moviesreviewthree.domain.model.Movie

sealed interface MoviesUIState {
    class Loading(val isLoading : Boolean) : MoviesUIState
    data class Success(val movie: List<Movie>, val endPoint: String) : MoviesUIState
    data class Error(val error : String) : MoviesUIState
}