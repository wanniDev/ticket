package io.ticketaka.api.common.domain.map

import io.ticketaka.api.user.domain.token.QueueToken

interface TokenWaitingMap {
    fun put(
        key: Long,
        queueToken: QueueToken,
    ): Boolean

    fun putAll(queueTokens: List<QueueToken>): Boolean

    fun get(key: Long): QueueToken?

    fun remove(key: Long): QueueToken?

    fun size(): Long

    fun findAll(): List<QueueToken>

    fun clear()
}
