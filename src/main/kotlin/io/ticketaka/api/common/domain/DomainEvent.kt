package io.ticketaka.api.common.domain

import java.time.LocalDateTime

interface DomainEvent {
    fun occurredOn(): LocalDateTime
}
