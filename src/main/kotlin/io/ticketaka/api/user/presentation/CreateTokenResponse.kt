package io.ticketaka.api.user.presentation

data class CreateTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
