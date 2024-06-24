package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointHistory

class PointChargeEventConsumer(
    private val pointEventService: PointEventService,
) : EventConsumer {
    override fun consume(events: List<DomainEvent>) {
        events.forEach { event ->
            if (event is PointChargeEvent) {
                pointEventService.appendEventLogInfo(event)
                pointEventService.recordPointHistory(event, PointHistory.TransactionType.CHARGE)
                pointEventService.retryableChargePoint(event)
            }
        }
    }
}
