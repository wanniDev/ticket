package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.common.domain.EventBroker
import org.springframework.stereotype.Component

@Component
class DBEventBroker(
    private val eventDispatcher: EventDispatcher,
) : EventBroker {
    override fun produceAndConsume(domainEvent: DomainEvent) {
        eventDispatcher.dispatchAndConsume(domainEvent)
    }
}
