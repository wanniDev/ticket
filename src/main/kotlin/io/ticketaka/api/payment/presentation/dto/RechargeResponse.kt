package io.ticketaka.api.payment.presentation.dto

import java.math.BigDecimal

data class RechargeResponse(
    val userId: String,
    val newBalance: BigDecimal
)
