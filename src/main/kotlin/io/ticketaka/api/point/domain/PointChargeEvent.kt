package io.ticketaka.api.point.domain

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.user.domain.User
import java.math.BigDecimal
import java.time.LocalDateTime

data class PointChargeEvent(
    val userId: Long,
    val pointId: Long,
    val balance: BigDecimal,
    val amount: BigDecimal,
    val occurredOn: LocalDateTime,
) : DomainEvent {
    constructor(user: User, point: Point, balance: BigDecimal, amount: BigDecimal) : this(
        user.id,
        point.id,
        balance,
        amount,
        LocalDateTime.now(),
    )

    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
