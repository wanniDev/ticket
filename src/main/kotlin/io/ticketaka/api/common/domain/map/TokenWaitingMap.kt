package io.ticketaka.api.common.domain.map

import io.ticketaka.api.user.domain.Token

interface TokenWaitingMap {
    fun put(
        key: String,
        token: Token,
    ): Boolean

    fun get(key: String): Token?

    fun remove(key: String): Token?

    fun size(): Long
}
