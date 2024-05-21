package io.ticketaka.api.user.infrastructure.event

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TokenEventHandler(
    private val tokenWaitingQueue: TokenWaitingQueue,
) {
    @Async
    @EventListener
    fun handle(event: TokenCreatedEvent) {
        val token = Token.newInstance(event.tsid, event.issuedTime, event.status, event.userId)
        tokenWaitingQueue.offer(token)
    }
}
