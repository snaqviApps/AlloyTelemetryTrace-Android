package com.digitssolutions.alloytelemetryandroid.placeholder.data.mapper

import com.digitssolutions.alloytelemetryandroid.core.domain.model.DataPlaceHolderInstance
import com.digitssolutions.alloytelemetryandroid.placeholder.data.dto.Result as DataPlaceHolderDtoResult



/**
 * returns the Domain-Model (Entity)
 */

fun DataPlaceHolderDtoResult.toDomain() : DataPlaceHolderInstance {
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