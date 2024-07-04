package io.ticketaka.api.point.domain.payment

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
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
) : AbstractAggregateRoot(),
    Persistable<Long> {
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    override fun getId(): Long = id

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    init {
        registerEvent(PaymentApprovalEvent(this, userId, pointId, amount))
    }

    companion object {
        fun newInstance(
            amount: BigDecimal,
            userId: Long,
            pointId: Long,
        ): Payment =
            Payment(
                id = TsIdKeyGenerator.nextLong(),
                amount = amount,
                userId = userId,
                paymentTime = LocalDateTime.now(),
                pointId = pointId,
            )
    }
}
