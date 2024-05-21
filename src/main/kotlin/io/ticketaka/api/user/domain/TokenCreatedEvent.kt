package io.ticketaka.api.user.domain

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.user.domain.Token.Status
import java.time.LocalDateTime

data class TokenCreatedEvent(
    var tsid: String,
    val issuedTime: LocalDateTime,
    val status: Status,
    val userId: Long,
    val occurredOn: LocalDateTime = LocalDateTime.now(),
) : DomainEvent {
    constructor(token: Token) : this(
        token.tsid,
        token.issuedTime,
        token.status,
        token.userId,
    )

    override fun occurredOn(): LocalDateTime {
        return this.occurredOn
    }
}
