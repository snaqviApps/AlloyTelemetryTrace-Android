package com.digitssolutions.moviesreviewthree.data.mapper

import com.digitssolutions.moviesreviewthree.domain.model.Movie
import com.digitssolutions.moviesreviewthree.data.movies.Result as MoviesDto

fun MoviesDto.toDomain() : Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        title = this.title,
        originCountry = this.originCountry,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview
    )
}