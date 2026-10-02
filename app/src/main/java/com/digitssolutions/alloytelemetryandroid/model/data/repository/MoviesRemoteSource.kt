package com.digitssolutions.alloytelemetryandroid.model.data.repository

import com.digitssolutions.alloytelemetryandroid.model.data.movies.DataPlaceHolder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MoviesRemoteSource {

    @GET
    suspend fun getMovies(
        @Url endPoint : String,
        @Query("page") page : Int = 1,
        @Query("api_key") apikey : String
        ) : Response<DataPlaceHolder>
}