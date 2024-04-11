package io.ticketaka.api.user.presentation

import java.time.LocalDateTime

data class PeekTokenResponse(
    val isTurn: Boolean,
    val occurredTime: LocalDateTime
)
