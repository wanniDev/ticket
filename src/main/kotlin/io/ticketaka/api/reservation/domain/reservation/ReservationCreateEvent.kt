package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.domain.DomainEvent
import java.time.LocalDateTime

data class ReservationCreateEvent(
    val userId: Long,
    val concertId: Long,
    val seatIds: List<Long>,
    val occurredOn: LocalDateTime = LocalDateTime.now(),
) : DomainEvent {
    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
