package io.ticketaka.api.common.infrastructure.queue

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.user.domain.token.QueueToken
import io.ticketaka.api.user.domain.token.TokenRepository

// @Component
class DBTokenWaitingQueue(
    private val tokenRepository: TokenRepository,
) : TokenWaitingQueue {
    override fun offer(element: QueueToken): Boolean {
        tokenRepository.save(element)
        return true
    }

    override fun poll(): QueueToken? {
        return tokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1()?.also {
            tokenRepository.delete(it)
        }
    }

    override fun peek(): QueueToken? {
        return tokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1()
    }

    override fun size(): Long {
        return tokenRepository.count()
    }
}
