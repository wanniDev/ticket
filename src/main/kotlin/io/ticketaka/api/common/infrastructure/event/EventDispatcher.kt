package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.infrastructure.event.PointChargeEventConsumer
import io.ticketaka.api.point.infrastructure.event.PointRechargeEventConsumer
import org.springframework.stereotype.Component

@Component
class EventDispatcher(
    private val pointRechargeEventConsumer: PointRechargeEventConsumer,
    private val pointChargeEventConsumer: PointChargeEventConsumer,
) {
    fun dispatchAndConsume(event: DomainEvent) {
        when (event) {
            is PointRechargeEvent -> {
                pointRechargeEventConsumer.offer(event)
            }
            is PointChargeEvent -> {
                pointChargeEventConsumer.offer(event)
            }
        }
    }
}
