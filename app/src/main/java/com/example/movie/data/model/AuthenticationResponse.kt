package com.example.movie.data.model

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("guest_session_id")
    val guestSessionId: String,
    @SerializedName("expires_at")
    val expiresAt: String,
)
