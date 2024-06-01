package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointEventHandler(
    private val eventBroker: EventBroker,
) {
    @EventListener
    fun handle(event: PointRechargeEvent) {
        eventBroker.produceAndConsume(event)
    }

    @EventListener
    fun handle(event: PointChargeEvent) {
        eventBroker.produceAndConsume(event)
    }
}
