package io.hhplus.ticket.payment.presentation

import java.math.BigDecimal

data class RechargeResponse(
    val userId: String,
    val newBalance: BigDecimal
)
