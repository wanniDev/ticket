package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

data class PaymentCommand(
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
)
