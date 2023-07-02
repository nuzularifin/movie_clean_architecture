package com.example.movie.data.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse (
    @SerializedName("pages") val page: Int,
    @SerializedName("results") val movie: List<Review>,
    @SerializedName("id") val totalPages: Int,
)