package io.ticketaka.api.payment.presentation

data class PayRequest(
    val userId: String,
    val reservationId: String,
    val amount: Long
)