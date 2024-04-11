package io.ticketaka.api.point.presentation.dto

import io.ticketaka.api.point.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userTsid: String,
    val amount: BigDecimal
) {
    fun toCommand() = RechargeCommand(
        userTsid = userTsid,
        amount = amount
    )
}
