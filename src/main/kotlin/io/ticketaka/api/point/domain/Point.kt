package io.ticketaka.api.point.domain

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "points")
class Point protected constructor(
    @Id
    val id: Long,
    var balance: BigDecimal,
    val createTime: LocalDateTime,
    var updateTime: LocalDateTime,
) : AbstractAggregateRoot() {
    fun recharge(amount: BigDecimal) {
        if (amount < BigDecimal.ZERO) throw BadClientRequestException("충전 금액은 0보다 커야 합니다.")
        this.balance = this.balance.plus(amount)
        this.updateTime = LocalDateTime.now()
    }

    fun charge(price: BigDecimal) {
        if (price <= BigDecimal.ZERO) throw BadClientRequestException("결제 금액은 0보다 커야 합니다.")
        if (this.balance < price) throw BadClientRequestException("잔액이 부족합니다.")
        this.balance = this.balance.minus(price)
    }

    fun rollback(balance: BigDecimal) {
        this.balance = balance
    }

    companion object {
        fun newInstance(balance: BigDecimal = BigDecimal.ZERO): Point {
            val now = LocalDateTime.now()
            return Point(
                id = TsIdKeyGenerator.nextLong(),
                balance = balance,
                createTime = now,
                updateTime = now,
            )
        }

        fun newInstance(id: Long): Point {
            return Point(
                id = id,
                balance = BigDecimal.ZERO,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
            )
        }
    }
}
