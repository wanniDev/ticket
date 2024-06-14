package io.ticketaka.api.user.domain.token

interface TokenParser {
    fun parse(token: String): QueueToken
}
