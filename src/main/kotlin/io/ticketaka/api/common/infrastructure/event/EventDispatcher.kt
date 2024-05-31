package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.reservation.domain.point.PointChargeEvent
import io.ticketaka.api.reservation.domain.point.PointRechargeEvent
import io.ticketaka.api.reservation.infrastructure.event.PointChargeEventConsumer
import io.ticketaka.api.reservation.infrastructure.event.PointRechargeEventConsumer
import org.springframework.stereotype.Component

@Component
class EventDispatcher(
    private val pointRechargeEventConsumer: PointRechargeEventConsumer,
    private val pointChargeEventConsumer: PointChargeEventConsumer,
) {
    fun dispatch(events: MutableList<DomainEvent>) {
        events.sortBy { it.occurredOn() }
        events.forEach { event ->
            when (event) {
                is PointRechargeEvent -> {
                    pointRechargeEventConsumer.pile(event)
                }
                is PointChargeEvent -> {
                    pointChargeEventConsumer.pile(event)
                }
            }
        }
    }
}
