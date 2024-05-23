package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.Seat

data class SeatResult(
    val seatNumber: String,
    val status: Seat.Status,
)
