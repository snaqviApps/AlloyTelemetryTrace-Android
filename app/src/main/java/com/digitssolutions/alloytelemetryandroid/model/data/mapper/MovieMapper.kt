package com.digitssolutions.alloytelemetryandroid.model.data.mapper

import com.digitssolutions.alloytelemetryandroid.domain.model.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.model.data.movies.Result as DataPlaceHolderDto

fun DataPlaceHolderDto.toDomain() : DataPlaceHolderInstance {
    return DataPlaceHolderInstance(
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