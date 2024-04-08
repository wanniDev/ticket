package io.ticketaka.api.payment.presentation.dto

import java.math.BigDecimal

data class RechargeRequest(
    val userId: String,
    val amount: BigDecimal
)
