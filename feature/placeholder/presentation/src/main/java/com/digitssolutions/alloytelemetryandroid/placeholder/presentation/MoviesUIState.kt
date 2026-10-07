package com.digitssolutions.alloytelemetryandroid.placeholder.presentation

import com.digitssolutions.alloytelemetryandroid.core.domain.model.DataPlaceHolderInstance

sealed interface MoviesUIState {
    class Loading(val isLoading : Boolean) : MoviesUIState
    data class Success(val dataPlaceHolderInstance: List<DataPlaceHolderInstance>, val endPoint: String) : MoviesUIState
    data class Error(val error : String) : MoviesUIState
}