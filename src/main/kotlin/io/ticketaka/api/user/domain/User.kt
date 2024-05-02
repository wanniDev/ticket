package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.reservation.domain.point.Point
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "users")
class User(
    @Id
    val tsid: String,
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point?,
) {
    fun rechargePoint(amount: BigDecimal) {
        this.point?.recharge(amount)
    }

    fun chargePoint(price: BigDecimal) {
        this.point?.charge(price)
    }

    fun getId(): Long {
        return id ?: throw IllegalStateException("User Id가 없습니다.")
    }

    var id: Long? = null

    companion object {
        fun newInstance(point: Point): User {
            return User(
                tsid = TsIdKeyGenerator.next("usr"),
                point = point,
            )
        }

        fun newInstance(tsid: String): User {
            return User(
                tsid = tsid,
                point = null,
            )
        }
    }
}
