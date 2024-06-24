package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent

interface EventConsumer {
    fun consume(events: List<DomainEvent>)
}
