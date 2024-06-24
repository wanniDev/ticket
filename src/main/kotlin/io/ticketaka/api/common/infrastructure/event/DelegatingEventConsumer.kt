package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import org.springframework.stereotype.Component

@Component
class DelegatingEventConsumer(
    private val pointEventService: PointEventService,
    private val reservationEventService: ReservationEventService,
) : EventConsumer {
    private val consumers = mutableListOf<EventConsumer>()

    init {
        listOf(
            PointChargeEventConsumer(pointEventService),
            PointRechargeEventConsumer(pointEventService),
            ReservationCreateEventConsumer(reservationEventService),
        ).forEach { consumers.add(it) }
    }

    override fun consume(events: List<DomainEvent>) {
        consumers.forEach { consumer ->
            consumer.consume(events)
        }
    }
}
