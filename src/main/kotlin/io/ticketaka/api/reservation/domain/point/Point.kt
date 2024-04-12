package io.ticketaka.api.reservation.domain.point

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Point protected constructor(
    val tsid: String,
    val balance: BigDecimal,
    val createTime: LocalDateTime,
    var updateTime: LocalDateTime
) {
    fun recharge(amount: BigDecimal) {
        if (amount <= BigDecimal.ZERO) throw IllegalArgumentException("충전 금액은 0보다 커야 합니다.")
        this.balance.plus(amount)
        this.updateTime = LocalDateTime.now()
    }

    fun charge(price: BigDecimal) {
        this.balance.minus(price)
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(balance: BigDecimal = BigDecimal.ZERO): Point {
            val now = LocalDateTime.now()
            return Point(
                tsid = TsIdKeyGenerator.next("pt"),
                balance = balance,
                createTime = now,
                updateTime = now
            )
        }
    }
}