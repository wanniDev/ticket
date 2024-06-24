package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.point.application.PointEventService
import io.ticketaka.api.point.infrastructure.event.PointChargeEventConsumer
import io.ticketaka.api.point.infrastructure.event.PointRechargeEventConsumer
import io.ticketaka.api.reservation.application.ReservationEventService
import io.ticketaka.api.reservation.infrastructure.event.ReservationCreateEventConsumer
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
