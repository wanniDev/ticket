package io.ticketaka.api.common.domain

interface EventBroker {
    fun produceAndConsume(domainEvent: DomainEvent)
}
