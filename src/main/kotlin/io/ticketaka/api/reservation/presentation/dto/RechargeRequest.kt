package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userTsid: String,
    val tokenTsid: String,
    val amount: BigDecimal,
) {
    fun toCommand() =
        RechargeCommand(
            userTsid = userTsid,
            tokenTsid = tokenTsid,
            amount = amount,
        )
}
