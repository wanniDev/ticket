package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.infrastructure.event.PointChargeEventQueue
import io.ticketaka.api.point.infrastructure.event.PointRechargeEventQueue
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.infrastructure.event.ReservationCreateEventQueue
import org.springframework.stereotype.Component

@Component
class EventBroker(
    private val pointRechargeEventQueue: PointRechargeEventQueue,
    private val pointChargeEventQueue: PointChargeEventQueue,
    private val reservationCreateEventQueue: ReservationCreateEventQueue,
) {
    fun dispatch(event: DomainEvent) {
        when (event) {
            is PointRechargeEvent -> {
                pointRechargeEventQueue.offer(event)
            }
            is PointChargeEvent -> {
                pointChargeEventQueue.offer(event)
            }
            is ReservationCreateEvent -> {
                reservationCreateEventQueue.offer(event)
            }
        }
    }
}
