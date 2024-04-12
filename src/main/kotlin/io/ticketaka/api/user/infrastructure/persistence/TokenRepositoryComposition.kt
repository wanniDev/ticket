package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.TokenRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaTokenRepository
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

    override fun findFirstTokenOrderByIssuedTimeAscLimit1(): Token {
        return jpaTokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1() ?: throw NotFoundException("토큰을 찾을 수 없습니다.")
    }

    override fun count(): Long {
        return jpaTokenRepository.count()
    }
}