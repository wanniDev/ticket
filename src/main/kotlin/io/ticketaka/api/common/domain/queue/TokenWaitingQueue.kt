package io.ticketaka.api.common.domain.queue

import io.ticketaka.api.user.domain.token.QueueToken

interface TokenWaitingQueue {
    fun offer(element: QueueToken): Boolean

    fun poll(): QueueToken?

    fun peek(): QueueToken?

    fun size(): Long
}
