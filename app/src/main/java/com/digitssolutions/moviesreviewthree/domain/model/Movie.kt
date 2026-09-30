package com.digitssolutions.moviesreviewthree.domain.model

data class Movie (
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>? = emptyList(),
    val id: Int? = -1,
    val title: String? = null,
    val originCountry : List<String>? = null,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
) : DomainEntity