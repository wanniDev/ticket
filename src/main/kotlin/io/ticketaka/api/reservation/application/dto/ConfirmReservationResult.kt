package io.ticketaka.api.reservation.application.dto

import io.ticketaka.api.reservation.domain.reservation.Reservation

data class ConfirmReservationResult(
    val reservationTsid: String,
    val status: Reservation.Status,
)
