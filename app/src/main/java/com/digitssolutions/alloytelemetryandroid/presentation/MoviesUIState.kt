package com.digitssolutions.alloytelemetryandroid.presentation

import com.digitssolutions.alloytelemetryandroid.domain.model.DataPlaceHolderInstance

sealed interface MoviesUIState {
    class Loading(val isLoading : Boolean) : MoviesUIState
    data class Success(val dataPlaceHolderInstance: List<DataPlaceHolderInstance>, val endPoint: String) : MoviesUIState
    data class Error(val error : String) : MoviesUIState
}