package io.ticketaka.api.point.domain

interface IdempotentRepository {
    fun save(idempotent: Idempotent): Idempotent

    fun findByKey(key: String): Idempotent?
}
