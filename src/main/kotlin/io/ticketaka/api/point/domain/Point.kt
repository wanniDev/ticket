package io.ticketaka.api.point.domain

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.domain.Persistable
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "points")
class Point protected constructor(
    @Id
    val id: Long,
    var balance: BigDecimal,
    val createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime,
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

    fun recharge(amount: BigDecimal) {
        if (amount < BigDecimal.ZERO) throw BadClientRequestException("충전 금액은 0보다 커야 합니다.")
        this.balance = this.balance.plus(amount)
        this.updatedAt = LocalDateTime.now()
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
        fun newInstance(
            id: Long,
            balance: BigDecimal,
            updateTime: LocalDateTime,
        ): Point {
            return Point(
                id = id,
                balance = balance,
                createdAt = null,
                updatedAt = updateTime,
            )
        }

        fun newInstance(balance: BigDecimal = BigDecimal.ZERO): Point {
            val now = LocalDateTime.now()
            return Point(
                id = TsIdKeyGenerator.nextLong(),
                balance = balance,
                createdAt = now,
                updatedAt = now,
            )
        }

        fun newInstance(id: Long): Point {
            return Point(
                id = id,
                balance = BigDecimal.ZERO,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        }
    }
}
