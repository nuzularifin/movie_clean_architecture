package com.example.movie.data.model

import com.example.movie.data.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("pages") val page: Int,
    @SerializedName("results") val movie: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
)