package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

data class BalanceQueryModel(
    val userTsid: String,
    val balance: BigDecimal
)