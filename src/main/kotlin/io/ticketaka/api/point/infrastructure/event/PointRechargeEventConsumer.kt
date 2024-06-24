package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.common.infrastructure.event.EventConsumer
import io.ticketaka.api.point.application.PointEventService
import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointRechargeEvent

class PointRechargeEventConsumer(
    private val pointEventService: PointEventService,
) : EventConsumer {
    override fun consume(events: List<DomainEvent>) {
        events.forEach { event ->
            if (event is PointRechargeEvent) {
                pointEventService.appendEventLogInfo(event)
                pointEventService.recordPointHistory(event, PointHistory.TransactionType.RECHARGE)
                pointEventService.retryableRechargePoint(event)
            }
        }
    }
}
