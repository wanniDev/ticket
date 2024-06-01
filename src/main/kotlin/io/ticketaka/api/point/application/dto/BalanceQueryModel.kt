package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

data class BalanceQueryModel(
    val userId: Long,
    val balance: BigDecimal,
)
