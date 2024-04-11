package io.ticketaka.api.user.presentation

import java.time.LocalDateTime

data class CreateTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
