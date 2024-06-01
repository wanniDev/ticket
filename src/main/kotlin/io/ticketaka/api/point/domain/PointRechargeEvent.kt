package io.ticketaka.api.point.domain

import io.ticketaka.api.common.domain.DomainEvent
import java.math.BigDecimal
import java.time.LocalDateTime

data class PointRechargeEvent(
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
    val occurredOn: LocalDateTime,
) : DomainEvent {
    constructor(userId: Long, pointId: Long, amount: BigDecimal) : this(userId, pointId, amount, LocalDateTime.now())

    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
