package io.ticketaka.api.point.infrastructure.event

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
        eventBroker.produce(event)
    }

    @EventListener
    fun handle(event: PointChargeEvent) {
        eventBroker.produce(event)
    }
}
