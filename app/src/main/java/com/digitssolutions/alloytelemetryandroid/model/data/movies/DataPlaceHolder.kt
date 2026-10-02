package com.digitssolutions.alloytelemetryandroid.model.data.movies

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName


// promise the compiler: "If this object equals its old self, skip our array index!"
@Stable
data class DataPlaceHolder(
    val page: Int,
    val results: List<Result>,      // Standard List is un-stable by default; @Stable fixes this.
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)