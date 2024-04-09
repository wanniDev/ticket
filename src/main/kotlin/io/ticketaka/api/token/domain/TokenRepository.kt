package io.ticketaka.api.token.domain

interface TokenRepository {
    fun save(token: Token): Token
    fun delete(token: Token)
    fun findFirstOrderByCreatedAtAsc(): Token?

    fun count(): Long
}