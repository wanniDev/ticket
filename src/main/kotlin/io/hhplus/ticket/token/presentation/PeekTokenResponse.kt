package io.hhplus.ticket.token.presentation

import java.time.LocalDateTime

data class PeekTokenResponse(
    val isTurn: Boolean,
    val occurredTime: LocalDateTime
)
