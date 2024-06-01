package io.ticketaka.api.reservation.presentation.dto

data class ConfirmReservationRequest(
    val userTsid: String,
    val reservationId: Long,
)
