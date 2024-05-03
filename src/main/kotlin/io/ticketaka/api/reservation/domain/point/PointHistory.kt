package io.ticketaka.api.reservation.domain.point

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "point_histories")
class PointHistory(
    val tsid: String,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
    val createTime: LocalDateTime = LocalDateTime.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

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
                tsid = TsIdKeyGenerator.next("ph"),
                userId = userId,
                pointId = pointId,
                amount = amount,
                transactionType = transactionType,
            )
        }
    }
}
