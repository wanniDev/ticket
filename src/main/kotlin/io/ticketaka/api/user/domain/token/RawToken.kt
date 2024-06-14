package io.ticketaka.api.user.domain.token

data class RawToken(
    val subject: String,
    val jti: String,
    val authorities: List<String>,
)
