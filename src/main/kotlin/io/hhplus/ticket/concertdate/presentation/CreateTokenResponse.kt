package io.hhplus.ticket.concertdate.presentation

import java.time.LocalDateTime

data class CreateTokenResponse(
    val token: String,
    val expiration: LocalDateTime
)
