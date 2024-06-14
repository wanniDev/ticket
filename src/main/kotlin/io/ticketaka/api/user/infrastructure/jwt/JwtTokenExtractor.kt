package io.ticketaka.api.user.infrastructure.jwt

import io.ticketaka.api.user.domain.token.TokenExtractor
import org.springframework.stereotype.Component

@Component
class JwtTokenExtractor : TokenExtractor {
    private val headerPrefix = "Bearer "

    override fun extract(payload: String): String {
        return if (payload.isEmpty()) {
            ""
        } else {
            payload.substring(headerPrefix.length)
        }
    }
}
