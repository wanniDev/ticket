package io.ticketaka.api.point.presentation.dto

import java.math.BigDecimal

data class BalanceResponse(
    val userId: String,
    val currentBalance: BigDecimal,
)
