package io.ticketaka.api.point.presentation.dto

import java.math.BigDecimal

data class RechargeResponse(
    val userId: String,
    val newBalance: BigDecimal
)