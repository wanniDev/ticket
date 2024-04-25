package io.ticketaka.api.reservation.application.dto

import java.math.BigDecimal

data class PaymentCommand(
    val userTsid: String,
    val amount: BigDecimal,
)
