package io.ticketaka.api.point.presentation.dto

import com.github.f4b6a3.tsid.TsidCreator
import io.ticketaka.api.point.application.dto.RechargeCommand
import java.math.BigDecimal

data class RechargeRequest(
    val userTsid: String,
    val amount: BigDecimal,
    val orderId: String,
    val cardNumber: String,
    val cardExpirationYear: Int,
    val cardExpirationMonth: Int,
    val cardPasswordPrefix: String,
    val customerIdentityNumber: String
) {
    fun toCommand() = RechargeCommand(
        userTsid = userTsid,
        amount = amount,
        orderId = "ord-${TsidCreator.getTsid().toLong()}",
        orderName = "포인트 충전",
        cardNumber = cardNumber,
        cardExpirationYear = cardExpirationYear,
        cardExpirationMonth = cardExpirationMonth,
        cardPasswordPrefix = cardPasswordPrefix,
        customerIdentityNumber = customerIdentityNumber
    )
}
