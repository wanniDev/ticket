package io.hhplus.ticket.reservation.presentation

import java.time.LocalDate

data class CreateReservationRequest(
    val userId: String,
    val date: LocalDate,
    val seatNumber: Int,
    val token: String
)