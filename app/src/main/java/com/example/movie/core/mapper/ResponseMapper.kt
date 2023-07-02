package com.example.movie.core.mapper

import com.example.movie.data.model.Authentication
import com.example.movie.data.model.AuthenticationResponse

fun AuthenticationResponse.toAuthentication(): Authentication {
    return Authentication(
        success = this.success,
        sessionId = this.guestSessionId,
        expiredAt = this.expiresAt
    )
}