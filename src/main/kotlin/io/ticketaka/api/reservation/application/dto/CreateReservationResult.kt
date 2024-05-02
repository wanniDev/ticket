package io.ticketaka.api.reservation.application.dto

import io.ticketaka.api.reservation.domain.reservation.Reservation
import java.time.LocalDateTime

data class CreateReservationResult(
    val reservationTsid: String,
    val status: Reservation.Status,
    val expiration: LocalDateTime,
)
