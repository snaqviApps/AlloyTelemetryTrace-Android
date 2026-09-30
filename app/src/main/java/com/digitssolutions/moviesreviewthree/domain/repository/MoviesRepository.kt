package com.digitssolutions.moviesreviewthree.domain.repository

import com.digitssolutions.moviesreviewthree.domain.model.DomainEntity
import com.digitssolutions.moviesreviewthree.domain.model.Movie

interface MoviesRepository<out T : DomainEntity>  {
    suspend fun getMovies(
        defaultCategory: String  = "popular",
        apiKey : String,
        page : Int
//    ) : List<Movie>
    ) : Result<List<Movie>>
}