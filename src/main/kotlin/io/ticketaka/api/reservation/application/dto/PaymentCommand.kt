package io.ticketaka.api.reservation.application.dto

import io.ticketaka.api.reservation.domain.payment.PaymentInfoData
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