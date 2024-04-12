package io.ticketaka.api.reservation.domain.point

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.*
import org.apache.coyote.BadRequestException
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
        if (amount <= BigDecimal.ZERO) throw BadClientRequestException("충전 금액은 0보다 커야 합니다.")
        this.balance.plus(amount)
        this.updateTime = LocalDateTime.now()
    }

    fun charge(price: BigDecimal) {
        if (price <= BigDecimal.ZERO) throw BadClientRequestException("결제 금액은 0보다 커야 합니다.")
        if (this.balance < price) throw BadClientRequestException("잔액이 부족합니다.")
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