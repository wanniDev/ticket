package io.ticketaka.api.user.infrastructure.event

import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenCreatedEvent
import io.ticketaka.api.user.domain.TokenRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TokenEventHandler(
    private val tokenRepository: TokenRepository,
) {
    @Async
    @EventListener
    fun handle(event: TokenCreatedEvent) {
        val token = Token.newInstance(event.userId)
        tokenRepository.save(token)
    }
}
