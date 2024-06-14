package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.user.domain.token.QueueToken
import io.ticketaka.api.user.domain.token.TokenRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaTokenRepository
import org.springframework.stereotype.Component

@Component
class TokenRepositoryComposition(
    private val jpaTokenRepository: JpaTokenRepository,
) : TokenRepository {
    override fun save(queueToken: QueueToken): QueueToken {
        return jpaTokenRepository.save(queueToken)
    }

    override fun delete(queueToken: QueueToken) {
        jpaTokenRepository.delete(queueToken)
    }

    override fun findFirstTokenOrderByIssuedTimeAscLimit1(): QueueToken? {
        return jpaTokenRepository.findFirstTokenOrderByIssuedTimeAscLimit1()
    }

    override fun count(): Long {
        return jpaTokenRepository.count()
    }
}
