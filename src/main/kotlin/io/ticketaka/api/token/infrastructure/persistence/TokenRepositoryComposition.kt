package io.ticketaka.api.token.infrastructure.persistence

import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.token.domain.TokenRepository
import org.springframework.stereotype.Component

@Component
class TokenRepositoryComposition: TokenRepository {
    override fun save(token: Token): Token {
        TODO("Not yet implemented")
    }

    override fun delete(token: Token) {
        TODO("Not yet implemented")
    }

    override fun findFirstOrderByCreatedAtAsc(): Token? {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}