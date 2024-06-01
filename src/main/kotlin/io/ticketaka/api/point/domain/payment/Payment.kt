package io.ticketaka.api.point.domain.payment

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    @Id
    val id: Long,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    val userId: Long,
    val pointId: Long,
) : AbstractAggregateRoot() {
    init {
        registerEvent(PaymentApprovalEvent(this, userId, pointId, amount))
    }

    companion object {
        fun newInstance(
            amount: BigDecimal,
            userId: Long,
            pointId: Long,
        ): Payment {
            return Payment(
                id = TsIdKeyGenerator.nextLong(),
                amount = amount,
                userId = userId,
                paymentTime = LocalDateTime.now(),
                pointId = pointId,
            )
        }
    }
}
