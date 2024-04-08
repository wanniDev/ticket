package io.ticketaka.api.balance.presentation.dto

import java.math.BigDecimal

data class BalanceResponse(
    val userId: String,
    val currentBalance: BigDecimal
)
