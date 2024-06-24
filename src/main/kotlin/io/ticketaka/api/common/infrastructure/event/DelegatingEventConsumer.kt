package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import org.springframework.stereotype.Component

@Component
class DelegatingEventConsumer(
    private val pointEventService: PointEventService,
) : EventConsumer {
    private val consumers = mutableListOf<EventConsumer>()

    init {
        listOf(
            PointChargeEventConsumer(pointEventService),
            PointRechargeEventConsumer(pointEventService),
        ).forEach { consumers.add(it) }
    }

    override fun consume(events: List<DomainEvent>) {
        consumers.forEach { consumer ->
            consumer.consume(events)
        }
    }
}
