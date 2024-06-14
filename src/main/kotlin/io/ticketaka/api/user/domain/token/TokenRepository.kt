package io.ticketaka.api.user.domain.token

interface TokenRepository {
    fun save(queueToken: QueueToken): QueueToken

    fun delete(queueToken: QueueToken)

    fun findFirstTokenOrderByIssuedTimeAscLimit1(): QueueToken?

    fun count(): Long
}
