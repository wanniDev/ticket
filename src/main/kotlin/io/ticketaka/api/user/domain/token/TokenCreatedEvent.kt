package io.ticketaka.api.user.domain.token

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.user.domain.token.QueueToken.Status
import java.time.LocalDateTime

data class TokenCreatedEvent(
    var id: Long,
    val issuedTime: LocalDateTime,
    val status: Status,
    val userId: Long,
    val occurredOn: LocalDateTime = LocalDateTime.now(),
) : DomainEvent {
    constructor(queueToken: QueueToken) : this(
        queueToken.id,
        queueToken.issuedTime,
        queueToken.status,
        queueToken.userId,
    )

    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
