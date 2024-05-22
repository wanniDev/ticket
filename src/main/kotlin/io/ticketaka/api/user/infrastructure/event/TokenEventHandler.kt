package io.ticketaka.api.user.infrastructure.event

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TokenEventHandler(
    private val tokenWaitingQueue: TokenWaitingMap,
) {
    @Async
    @EventListener
    fun handle(event: TokenCreatedEvent) {
        if (tokenWaitingQueue.size() >= 500) {
            throw IllegalStateException("토큰 용량이 초과되었습니다.")
        }
        val token = Token.newInstance(event.tsid, event.issuedTime, event.status, event.userId)
        tokenWaitingQueue.put(event.tsid, token)
    }
}
