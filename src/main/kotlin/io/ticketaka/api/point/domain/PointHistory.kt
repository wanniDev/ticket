package io.ticketaka.api.point.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "point_histories")
class PointHistory(
    @Id
    val id: Long = 0,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
    val createTime: LocalDateTime = LocalDateTime.now(),
) : Persistable<Long> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return isNew
    }

    override fun getId(): Long {
        return id
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    enum class TransactionType {
        RECHARGE,
        CHARGE,
    }

    companion object {
        fun newInstance(
            userId: Long,
            pointId: Long,
            amount: BigDecimal,
            transactionType: TransactionType,
        ): PointHistory {
            return PointHistory(
                id = TsIdKeyGenerator.nextLong(),
                userId = userId,
                pointId = pointId,
                amount = amount,
                transactionType = transactionType,
            )
        }
    }
}
