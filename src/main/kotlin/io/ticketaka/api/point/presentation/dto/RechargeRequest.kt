package io.ticketaka.api.point.presentation.dto

import io.ticketaka.api.point.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
) {
    fun toCommand() =
        RechargeCommand(
            userId = userId,
            pointId = pointId,
            amount = amount,
        )
}
