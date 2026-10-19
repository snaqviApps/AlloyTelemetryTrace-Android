package com.digitssolutions.alloytelemetryandroid.core.domain.repository

import com.digitssolutions.alloytelemetryandroid.core.domain.DomainEntity
import com.digitssolutions.alloytelemetryandroid.core.domain.model.PlaceHolderEntity


interface MoviesRepository<out T : DomainEntity>  {
    suspend fun getMovies(
        defaultCategory: String  = "popular",
        apiKey : String,
        page : Int
    ) : Result<List<PlaceHolderEntity>>    // currently fetching List<Movie>
}