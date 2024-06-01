package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

class RechargeCommand(
    val userTsid: String,
    val pointId: Long,
    val amount: BigDecimal,
)
