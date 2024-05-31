package io.ticketaka.api.common.domain

interface EventBroker {
    fun produce(domainEvent: DomainEvent)
}
