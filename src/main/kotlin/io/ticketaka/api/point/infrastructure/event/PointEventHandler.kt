package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.common.infrastructure.event.EventProducer
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointEventHandler(
    private val eventProducer: EventProducer,
) {
    @EventListener
    fun handle(event: PointRechargeEvent) {
        eventProducer.produce(event)
    }

    @EventListener
    fun handle(event: PointChargeEvent) {
        eventProducer.produce(event)
    }
}
