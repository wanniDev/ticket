package io.ticketaka.api.reservation.presentation.dto

import java.math.BigDecimal

data class BalanceResponse(
    val userId: String,
    val currentBalance: BigDecimal,
)
