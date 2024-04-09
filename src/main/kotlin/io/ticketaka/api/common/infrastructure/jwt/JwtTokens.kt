package io.ticketaka.api.common.infrastructure.jwt

data class JwtTokens(
    val accessToken: String,
    val refreshToken: String
)
