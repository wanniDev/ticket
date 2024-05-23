package io.ticketaka.api.common.infrastructure

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.Token
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class InMemoryWaitingMap : TokenWaitingMap {
    private val tokenCache: Cache<String, Token> =
        Caffeine.newBuilder()
            .initialCapacity(500)
            .recordStats()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .maximumSize(500)
            .build()

    override fun put(
        key: String,
        token: Token,
    ): Boolean {
        tokenCache.put(key, token)
        return true
    }

    override fun putAll(tokens: List<Token>): Boolean {
        tokenCache.putAll(tokens.associateBy { it.tsid })
        return true
    }

    override fun get(key: String): Token? {
        return tokenCache.getIfPresent(key)
    }

    override fun findAll(): List<Token> {
        return tokenCache.asMap().values.toList()
    }

    override fun clear() {
        tokenCache.invalidateAll()
    }

    override fun remove(key: String): Token? {
        return tokenCache.asMap().remove(key)
    }

    override fun size(): Long {
        return tokenCache.estimatedSize()
    }
}
