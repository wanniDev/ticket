package io.ticketaka.api.reservation.domain.point

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.user.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "point_histories")
class PointHistory(
    @Id
    val tsid: String,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point,
) {
    var id: Long? = null

    enum class TransactionType {
        RECHARGE,
        CHARGE,
    }

    companion object {
        fun newInstance(
            userTsid: String,
            pointTsid: String,
            transactionType: TransactionType,
        ): PointHistory {
            return PointHistory(
                tsid = TsIdKeyGenerator.next("ph"),
                user = User.newInstance(tsid = userTsid),
                point = Point.newInstance(tsid = pointTsid),
                transactionType = transactionType,
            )
        }
    }
}
