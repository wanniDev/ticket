package io.ticketaka.api.reservation.application.dto

import java.math.BigDecimal

class RechargeCommand(
    val userTsid: String,
    val amount: BigDecimal,
)
