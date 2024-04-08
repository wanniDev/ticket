package io.ticketaka.api.balance.presentation.dto

import java.math.BigDecimal

data class RechargeResponse(
    val userId: String,
    val newBalance: BigDecimal
)
