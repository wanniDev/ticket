package io.ticketaka.api.reservation.presentation.dto

data class ConfirmReservationRequest(
    val userId: Long,
    val reservationId: Long,
)
