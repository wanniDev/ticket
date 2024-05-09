package io.ticketaka.api.concert.presentation.dto

import io.ticketaka.api.concert.domain.Seat

data class SeatResponse(
    val seatNumber: String,
    val status: Seat.Status,
)
