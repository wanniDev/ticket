package io.ticketaka.api.user.infrastructure.event

import com.github.benmanes.caffeine.cache.Cache
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class TokenEventHandler(
    private val tokenCache: Cache<String, Token>,
) {
    @Async
    @EventListener
    fun handle(event: TokenCreatedEvent) {
        val token = Token.newInstance(event.tsid, event.issuedTime, event.status, event.userId)
        tokenCache.put(token.tsid, token)
    }
}
