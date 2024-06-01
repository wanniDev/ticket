package io.ticketaka.api.point.domain

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.user.domain.User
import java.math.BigDecimal
import java.time.LocalDateTime

data class PointRechargeEvent(
    val userTsid: String,
    val userId: Long,
    val pointId: Long,
    val amount: BigDecimal,
    val occurredOn: LocalDateTime,
) : DomainEvent {
    constructor(user: User, point: Point, amount: BigDecimal) : this(user.tsid, user.getId(), point.id, amount, LocalDateTime.now())

    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
