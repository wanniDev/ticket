package io.ticketaka.api.point.presentation.dto

import java.math.BigDecimal

data class RechargeRequest(
    val userId: String,
    val amount: BigDecimal
)
