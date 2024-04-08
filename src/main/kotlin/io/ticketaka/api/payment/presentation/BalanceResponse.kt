package io.ticketaka.api.payment.presentation

import java.math.BigDecimal

data class BalanceResponse(
    val userId: String,
    val currentBalance: BigDecimal
)
