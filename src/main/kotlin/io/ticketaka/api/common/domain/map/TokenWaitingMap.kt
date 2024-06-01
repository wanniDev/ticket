package io.ticketaka.api.common.domain.map

import io.ticketaka.api.user.domain.Token

interface TokenWaitingMap {
    fun put(
        key: Long,
        token: Token,
    ): Boolean

    fun putAll(tokens: List<Token>): Boolean

    fun get(key: Long): Token?

    fun remove(key: Long): Token?

    fun size(): Long

    fun findAll(): List<Token>

    fun clear()
}
