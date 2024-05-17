package io.ticketaka.api.reservation.domain.payment

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    val tsid: String,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    val userId: Long,
    val pointId: Long,
) : AbstractAggregateRoot() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

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
                tsid = TsIdKeyGenerator.next("pm"),
                amount = amount,
                userId = userId,
                paymentTime = LocalDateTime.now(),
                pointId = pointId,
            )
        }
    }
}
