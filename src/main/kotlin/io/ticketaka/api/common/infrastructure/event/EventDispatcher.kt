package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.concert.infrastructure.event.ReservationCreateEventConsumer
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.infrastructure.event.PointChargeEventConsumer
import io.ticketaka.api.point.infrastructure.event.PointRechargeEventConsumer
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import org.springframework.stereotype.Component

@Component
class EventDispatcher(
    private val pointRechargeEventConsumer: PointRechargeEventConsumer,
    private val pointChargeEventConsumer: PointChargeEventConsumer,
    private val reservationCreateEventConsumer: ReservationCreateEventConsumer,
) {
    fun dispatch(event: DomainEvent) {
        when (event) {
            is PointRechargeEvent -> {
                pointRechargeEventConsumer.offer(event)
            }
            is PointChargeEvent -> {
                pointChargeEventConsumer.offer(event)
            }
            is ReservationCreateEvent -> {
                reservationCreateEventConsumer.offer(event)
            }
        }
    }
}
