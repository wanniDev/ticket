package io.ticketaka.api.common.infrastructure.queue

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.user.domain.Token
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class InMemoryWaitingQueue(
    private val tokenCache: Cache<String, Token> =
        Caffeine.newBuilder()
            .initialCapacity(500)
            .recordStats()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(500)
            .build(),
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
        return tokenCache.asMap().values.minByOrNull { it.issuedTime }
    }

    override fun size(): Long {
        return tokenCache.estimatedSize()
    }
}
