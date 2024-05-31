package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.reservation.domain.point.PointChargeEvent
import io.ticketaka.api.reservation.domain.point.PointRechargeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointEventHandler(
    private val eventBroker: EventBroker,
) {
    @EventListener
    fun handle(event: PointRechargeEvent) {
        eventBroker.produce(event)
    }

    @EventListener
    fun handle(event: PointChargeEvent) {
        eventBroker.produce(event)
    }
}
