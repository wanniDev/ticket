package io.ticketaka.api.token.infrastructure.persistence

import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.token.domain.TokenRepository
import io.ticketaka.api.token.infrastructure.jpa.JpaTokenRepository
import org.springframework.stereotype.Component

@Component
class TokenRepositoryComposition(
    private val jpaTokenRepository: JpaTokenRepository
): TokenRepository {
    override fun save(token: Token): Token {
        return jpaTokenRepository.save(token)
    }

    override fun delete(token: Token) {
        jpaTokenRepository.delete(token)
    }

    override fun findFirstTokenOrderByIssuedTimeAscLimit1(): Token? {
        return jpaTokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1()
    }

    override fun count(): Long {
        return jpaTokenRepository.count()
    }
}