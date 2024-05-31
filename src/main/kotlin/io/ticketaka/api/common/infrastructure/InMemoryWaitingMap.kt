package io.ticketaka.api.common.infrastructure

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.Token
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryWaitingMap : TokenWaitingMap {
    private val tokenCache: MutableMap<String, Token> = ConcurrentHashMap()

    override fun put(
        key: String,
        token: Token,
    ): Boolean {
        tokenCache[key] = token
        return true
    }

    override fun putAll(tokens: List<Token>): Boolean {
        tokenCache.putAll(tokens.map { it.tsid to it })
        return true
    }

    override fun get(key: String): Token? {
        return tokenCache[key]
    }

    override fun findAll(): List<Token> {
        return tokenCache.values.toList()
    }

    override fun clear() {
        tokenCache.clear()
    }

    override fun remove(key: String): Token? {
        return tokenCache.remove(key)
    }

    override fun size(): Long {
        synchronized(this) {
            return tokenCache.size.toLong()
        }
    }
}
