package io.ticketaka.api.user.domain

interface UserRepository {
    fun save(user: User): User

    fun findById(id: Long): User?

    fun findByEmail(email: String): User?
}
