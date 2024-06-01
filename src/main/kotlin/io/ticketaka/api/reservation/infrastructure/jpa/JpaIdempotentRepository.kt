package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.point.domain.Idempotent
import org.springframework.data.jpa.repository.JpaRepository

interface JpaIdempotentRepository : JpaRepository<Idempotent, String> {
    fun findByKey(key: String): Idempotent?
}
