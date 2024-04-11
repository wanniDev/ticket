package io.ticketaka.api.point.application.dto

import java.math.BigDecimal

class RechargeCommand(
    val userTsid: String,
    val amount: BigDecimal,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
    val cardPasswordPrefix: String,
    val customerIdentityNumber: String
)