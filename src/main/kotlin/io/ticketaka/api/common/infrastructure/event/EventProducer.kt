package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import org.springframework.stereotype.Component

@Component
class EventProducer(
    private val eventBroker: EventBroker,
) {
    fun produce(domainEvent: DomainEvent) {
        eventBroker.dispatch(domainEvent)
    }
}
