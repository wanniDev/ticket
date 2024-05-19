package io.ticketaka.api.reservation.domain.payment

import io.ticketaka.api.common.domain.DomainEvent
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentApprovalEvent(
    val payment: Payment,
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
) : DomainEvent {
    override fun occurredOn(): LocalDateTime {
        return payment.paymentTime
    }
}
