package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.common.infrastructure.event.EventProducer
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReservationEventHandler(
    private val eventProducer: EventProducer,
) {
    @EventListener
    fun handle(event: ReservationCreateEvent) {
        eventProducer.produce(event)
    }
}
