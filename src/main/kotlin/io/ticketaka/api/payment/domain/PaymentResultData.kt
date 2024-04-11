package io.ticketaka.api.payment.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentResultData(
    val transactionKey: String,
    val amount: BigDecimal,
    val requestedTime: LocalDateTime,
    val approvedTime: LocalDateTime,
    val status: String
)
