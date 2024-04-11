package io.ticketaka.api.payment.domain

import java.math.BigDecimal

data class PaymentInfoData(
    val amount: BigDecimal,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
    val cardPasswordPrefix: String,
    val customerIdentityNumber: String
)
