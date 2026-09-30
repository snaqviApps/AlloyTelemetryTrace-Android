package com.digitssolutions.moviesreviewthree.data.repository

import com.digitssolutions.moviesreviewthree.data.mapper.toDomain
import com.digitssolutions.moviesreviewthree.domain.model.Movie
import com.digitssolutions.moviesreviewthree.domain.repository.MoviesRepository
import javax.inject.Inject


/**
 * MoviesRepository implementation
 */
class DefaultMoviesRepository @Inject constructor(
    private val moviesRemoteSource: MoviesRemoteSource
) : MoviesRepository<Movie> {
    override suspend fun getMovies(
        defaultCategory: String,
        apiKey: String,
        page: Int
    ): Result<List<Movie>> {
        return try {
            moviesRemoteSource.getMovies(
                endPoint = defaultCategory,
                page = page,
                apikey = apiKey
            ).run {
                if (this.isSuccessful) {
                    val body = body()
                    if (body != null) {
                        val movies: List<Movie> = body.results.map { it.toDomain() }
                        Result.success(movies)
                    } else {
                        Result.failure(Exception("Exception "))
                    }
                } else {
                    Result.failure(Exception("Exception failed with ${code()}"))
                }
            }

        } catch (e: Exception) {
            Result.failure(Exception("Exception failed with ${e.message}"))
        }
    }

}
