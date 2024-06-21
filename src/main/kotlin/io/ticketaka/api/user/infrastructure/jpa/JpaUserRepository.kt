package io.ticketaka.api.user.infrastructure.jpa

import io.ticketaka.api.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
