package io.ticketaka.api.point.presentation.dto

import io.ticketaka.api.reservation.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userTsid: String,
    val pointTsid: String,
    val amount: BigDecimal,
) {
    fun toCommand() =
        RechargeCommand(
            userTsid = userTsid,
            pointTsid = pointTsid,
            amount = amount,
        )
}
