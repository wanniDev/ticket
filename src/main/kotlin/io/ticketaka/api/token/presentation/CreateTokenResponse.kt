package io.ticketaka.api.token.presentation

import java.time.LocalDateTime

data class CreateTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
