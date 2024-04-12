package io.ticketaka.api.user.domain

import io.ticketaka.api.user.domain.Token

interface TokenRepository {
    fun save(token: Token): Token
    fun delete(token: Token)
    fun findFirstTokenOrderByIssuedTimeAscLimit1(): Token

    fun count(): Long
}