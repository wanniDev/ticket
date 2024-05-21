package io.ticketaka.api.common.infrastructure.queue

import com.github.benmanes.caffeine.cache.Cache
import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.user.domain.Token

// @Component
class InMemoryWaitingQueue(
    private val tokenCache: Cache<String, Token>,
) : TokenWaitingQueue {
    override fun offer(element: Token): Boolean {
        tokenCache.put(element.tsid, element)
        return true
    }

    override fun poll(): Token? {
        return tokenCache.asMap().values.minByOrNull { it.issuedTime }?.also {
            tokenCache.invalidate(it.tsid)
        }
    }

    override fun peek(): Token? {
        println(tokenCache.asMap().values)
        return tokenCache.asMap().values.minByOrNull { it.issuedTime }
    }

    override fun size(): Long {
        return tokenCache.estimatedSize()
    }
}
