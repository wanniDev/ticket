package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.domain.reservation.Reservation

data class ConfirmReservationResponse(
    val reservationTsid: String,
    val status: Reservation.Status,
)
