package io.ticketaka.api.user.domain.token

interface TokenExtractor {
    fun extract(payload: String?): String
}
