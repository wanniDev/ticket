package io.ticketaka.api.common.infrastructure.queue

import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.token.domain.TokenRepository
import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import org.springframework.stereotype.Component

@Component
class DBTokenWaitingQueue(
    private val tokenRepository: TokenRepository
): TokenWaitingQueue {
    override fun offer(element: Token): Boolean {
        tokenRepository.save(element)
        return true
    }

    override fun poll(): Token? {
        return tokenRepository.findFirstOrderByCreatedAtAsc()?.also {
            tokenRepository.delete(it)
        }
    }

    override fun peek(): Token? {
        return tokenRepository.findFirstOrderByCreatedAtAsc()
    }

    override fun size(): Long {
        return tokenRepository.count()
    }
}