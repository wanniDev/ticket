package io.ticketaka.api.common.infrastructure

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.token.QueueToken
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryWaitingMap : TokenWaitingMap {
    private val queueTokenCache: MutableMap<Long, QueueToken> = ConcurrentHashMap()

    override fun put(
        key: Long,
        queueToken: QueueToken,
    ): Boolean {
        queueTokenCache[key] = queueToken
        return true
    }

    override fun putAll(queueTokens: List<QueueToken>): Boolean {
        queueTokenCache.putAll(queueTokens.map { it.id to it })
        return true
    }

    override fun get(key: Long): QueueToken? {
        return queueTokenCache[key]
    }

    override fun findAll(): List<QueueToken> {
        return queueTokenCache.values.toList()
    }

    override fun clear() {
        queueTokenCache.clear()
    }

    override fun remove(key: Long): QueueToken? {
        return queueTokenCache.remove(key)
    }

    override fun size(): Long {
        synchronized(this) {
            return queueTokenCache.size.toLong()
        }
    }
}
