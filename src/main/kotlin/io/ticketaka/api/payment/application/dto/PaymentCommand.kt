package io.ticketaka.api.payment.application.dto

import io.ticketaka.api.payment.domain.PaymentInfoData
import java.math.BigDecimal

data class PaymentCommand(
    val userTsid: String,
    val amount: BigDecimal,
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
    val cardPasswordPrefix: String,
    val customerIdentityNumber: String
) {
    fun toDomain() = PaymentInfoData(
        userTsid = userTsid,
        amount = amount,
        orderId = orderId,
        orderName = orderName,
        cardNumber = cardNumber,
        cardExpirationYear = cardExpirationYear,
        cardExpirationMonth = cardExpirationMonth,
        cardPasswordPrefix = cardPasswordPrefix,
        customerIdentityNumber = customerIdentityNumber
    )
}