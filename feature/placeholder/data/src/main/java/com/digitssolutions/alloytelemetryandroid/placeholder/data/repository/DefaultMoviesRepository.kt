package com.digitssolutions.alloytelemetryandroid.placeholder.data.repository

import com.digitssolutions.alloytelemetryandroid.placeholder.data.mapper.toDomain
import com.digitssolutions.alloytelemetryandroid.core.domain.model.PlaceHolderEntity
import com.digitssolutions.alloytelemetryandroid.core.domain.repository.MoviesRepository
import javax.inject.Inject

/**
 * MoviesRepository implementation
 */
class DefaultMoviesRepository @Inject constructor (
    private val moviesRemoteSource: MoviesRemoteSource
) : MoviesRepository<PlaceHolderEntity> {
    override suspend fun getMovies(
        defaultCategory: String,
        apiKey: String,
        page: Int
    ): Result<List<PlaceHolderEntity>> {
        return try {
            moviesRemoteSource.getMovies(
                endPoint = defaultCategory,
                page = page,
                apikey = apiKey
            ).run {
                if (this.isSuccessful) {
                    val body = body()
                    if (body != null) {
                        val placeHolderEntity: List<PlaceHolderEntity> = body
                            .results
                            .map { rawResult -> rawResult.toDomain() }
                        Result.success(placeHolderEntity)
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
