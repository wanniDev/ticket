package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.domain.reservation.Reservation
import java.time.LocalDateTime

data class CreateReservationResponse(
    val reservationTsid: String,
    val status: Reservation.Status,
    val expirationTime: LocalDateTime,
)
