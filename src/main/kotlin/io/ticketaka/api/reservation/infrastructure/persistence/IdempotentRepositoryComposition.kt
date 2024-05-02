package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.point.Idempotent
import io.ticketaka.api.reservation.domain.point.IdempotentRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaIdempotentRepository
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
