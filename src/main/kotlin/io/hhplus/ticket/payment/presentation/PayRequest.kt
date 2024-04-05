package io.hhplus.ticket.payment.presentation

data class PayRequest(
    val userId: String,
    val reservationId: String,
    val amount: Long
)
