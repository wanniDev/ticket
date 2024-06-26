package io.ticketaka.api.user.infrastructure.event

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.common.exception.TooManyRequestException
import io.ticketaka.api.user.domain.token.QueueToken
import io.ticketaka.api.user.domain.token.TokenCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TokenEventHandler(
    private val tokenWaitingQueue: TokenWaitingMap,
) {
    @EventListener
    fun handle(event: TokenCreatedEvent) {
        if (tokenWaitingQueue.size() >= 500) {
            throw TooManyRequestException("토큰 용량이 초과되었습니다.")
        }
        val queueToken = QueueToken.newInstance(event.id, event.issuedTime, event.status, event.userId)
        tokenWaitingQueue.put(event.id, queueToken)
    }
}
