package com.digitssolutions.alloytelemetryandroid.domain.repository

import com.digitssolutions.alloytelemetryandroid.domain.model.DomainEntity
import com.digitssolutions.alloytelemetryandroid.domain.model.DataPlaceHolderInstance

interface MoviesRepository<out T : DomainEntity>  {
    suspend fun getMovies(
        defaultCategory: String  = "popular",
        apiKey : String,
        page : Int
//    ) : List<Movie>
    ) : Result<List<DataPlaceHolderInstance>>
}