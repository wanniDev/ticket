package io.ticketaka.api.user.domain

interface TokenRepository {
    fun save(token: Token): Token

    fun delete(token: Token)

    fun findFirstTokenOrderByIssuedTimeAscLimit1(): Token?

    fun count(): Long
}
