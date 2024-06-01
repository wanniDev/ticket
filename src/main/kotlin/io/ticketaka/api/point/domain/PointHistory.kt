package io.ticketaka.api.point.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "point_histories")
class PointHistory(
    @Id
    val id: Long,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
    val createTime: LocalDateTime = LocalDateTime.now(),
) {
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
