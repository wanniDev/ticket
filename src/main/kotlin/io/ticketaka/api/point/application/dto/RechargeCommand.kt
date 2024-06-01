package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

class RechargeCommand(
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
)
