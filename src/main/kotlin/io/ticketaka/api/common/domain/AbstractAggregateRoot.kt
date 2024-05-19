package io.ticketaka.api.common.domain

abstract class AbstractAggregateRoot {
    private val events = mutableListOf<DomainEvent>()

    protected fun registerEvent(domainEvent: DomainEvent) {
        this.events.add(domainEvent)
    }

    fun pollAllEvents(): List<DomainEvent> {
        return if (this.events.isNotEmpty()) {
            val domainEvents = events.toList()
            this.events.clear()
            domainEvents
        } else {
            emptyList()
        }
    }
}
