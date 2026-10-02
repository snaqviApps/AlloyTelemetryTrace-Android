package com.digitssolutions.alloytelemetryandroid.model.data.repository

import com.digitssolutions.alloytelemetryandroid.model.data.mapper.toDomain
import com.digitssolutions.alloytelemetryandroid.domain.model.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.domain.repository.MoviesRepository
import javax.inject.Inject

/**
 * MoviesRepository implementation
 */
class DefaultMoviesRepository @Inject constructor (
    private val moviesRemoteSource: MoviesRemoteSource
) : MoviesRepository<DataPlaceHolderInstance> {
    override suspend fun getMovies(
        defaultCategory: String,
        apiKey: String,
        page: Int
    ): Result<List<DataPlaceHolderInstance>> {
        return try {
            moviesRemoteSource.getMovies(
                endPoint = defaultCategory,
                page = page,
                apikey = apiKey
            ).run {
                if (this.isSuccessful) {
                    val body = body()
                    if (body != null) {
                        val dataPlaceHolderInstances: List<DataPlaceHolderInstance> = body.results.map { it.toDomain() }
                        Result.success(dataPlaceHolderInstances)
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
