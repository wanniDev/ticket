package io.hhplus.ticket.token.presentation

import java.time.LocalDateTime

data class CreateTokenResponse(
    val token: String,
    val expiration: LocalDateTime
)
