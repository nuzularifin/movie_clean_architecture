package com.example.movie.data.model

data class Authentication(
    val success: Boolean,
    val sessionId: String,
    val expiredAt: String,
)