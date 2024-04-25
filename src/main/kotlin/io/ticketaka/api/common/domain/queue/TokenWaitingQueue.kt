package io.ticketaka.api.common.domain.queue

import io.ticketaka.api.user.domain.Token

interface TokenWaitingQueue {
    fun offer(element: Token): Boolean

    fun poll(): Token?

    fun peek(): Token?

    fun size(): Long
}
