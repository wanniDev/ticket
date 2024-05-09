package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.Seat

data class SeatResult(
    val number: String,
    val status: Seat.Status,
)
