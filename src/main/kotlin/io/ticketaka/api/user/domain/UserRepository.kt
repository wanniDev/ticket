package io.ticketaka.api.user.domain

interface UserRepository {
    fun findById(id: Long): User?
}
