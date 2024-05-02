package io.ticketaka.api.reservation.domain.point

interface IdempotentRepository {
    fun save(idempotent: Idempotent): Idempotent

    fun findByKey(key: String): Idempotent?
}
