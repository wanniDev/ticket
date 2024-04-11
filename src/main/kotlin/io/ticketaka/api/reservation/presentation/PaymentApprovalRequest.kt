package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import java.math.BigDecimal

data class PaymentApprovalRequest(
    val userTsid: String,
    val amount: BigDecimal,
    val cardNumber: String,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
    val cardPasswordPrefix: String,
    val customerIdentityNumber: String
) {
    fun toCommand() = PaymentCommand(
        userTsid = userTsid,
        amount = amount,
        orderId = TsIdKeyGenerator.next("ord"),
        orderName = "결제 승인 요청",
        cardNumber = cardNumber,
        cardExpirationYear = cardExpirationYear,
        cardExpirationMonth = cardExpirationMonth,
        cardPasswordPrefix = cardPasswordPrefix,
        customerIdentityNumber = customerIdentityNumber
    )
}
