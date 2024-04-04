package io.hhplus.ticket.reservation.presentation

import io.hhplus.ticket.reservation.domain.Reservation
import java.time.LocalDateTime

data class CreateReservationResponse(
    val reservationId: String,
    val status: Reservation.Status,
    val expirationTime: LocalDateTime
)
