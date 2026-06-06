package com.shindoclient.spotify.auth

data class AuthToken(
    val accessToken: String,
    val refreshToken: String?,
    val expiresIn: Int = 3600,
)
