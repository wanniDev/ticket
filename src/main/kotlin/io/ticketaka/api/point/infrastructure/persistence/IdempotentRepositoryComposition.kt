package io.ticketaka.api.point.infrastructure.persistence

import io.ticketaka.api.point.domain.Idempotent
import io.ticketaka.api.point.domain.IdempotentRepository
import io.ticketaka.api.point.infrastructure.jpa.JpaIdempotentRepository
import org.springframework.stereotype.Repository

@Repository
class IdempotentRepositoryComposition(
    private val jpaIdempotentRepository: JpaIdempotentRepository,
) : IdempotentRepository {
    override fun save(idempotent: Idempotent): Idempotent {
        return jpaIdempotentRepository.save(idempotent)
    }

    override fun findByKey(key: String): Idempotent? {
        return jpaIdempotentRepository.findByKey(key)
    }
}
