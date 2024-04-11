package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.domain.Reservation
import java.time.LocalDateTime

data class CreateReservationResponse(
    val reservationId: String,
    val status: Reservation.Status,
    val expirationTime: LocalDateTime
)
