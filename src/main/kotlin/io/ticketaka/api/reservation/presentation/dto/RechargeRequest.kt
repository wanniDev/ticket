package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userTsid: String,
    val amount: BigDecimal,
) {
    fun toCommand() =
        RechargeCommand(
            userTsid = userTsid,
            amount = amount,
        )
}
