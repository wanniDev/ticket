package io.ticketaka.api.user.domain.token

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)
