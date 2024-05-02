package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.point.Idempotent
import org.springframework.data.jpa.repository.JpaRepository

interface JpaIdempotentRepository : JpaRepository<Idempotent, String> {
    fun findByKey(key: String): Idempotent?
}
