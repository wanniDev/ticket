package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent

class ReservationCreateEventConsumer(
    private val reservationEventService: ReservationEventService,
) : EventConsumer {
    override fun consume(events: List<DomainEvent>) {
        events.forEach { event ->
            if (event is ReservationCreateEvent) {
                reservationEventService.appendEventLogInfo(event)
                reservationEventService.retryableCreateReservation(event)
            }
        }
    }
}
