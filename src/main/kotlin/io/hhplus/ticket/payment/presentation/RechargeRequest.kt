package io.hhplus.ticket.payment.presentation

import java.math.BigDecimal

data class RechargeRequest(
    val userId: String,
    val amount: BigDecimal
)
