package io.ticketaka.api.user.infrastructure.jpa

import io.ticketaka.api.user.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : JpaRepository<User, Long> {
    @EntityGraph(attributePaths = ["point"])
    fun findByTsid(userTsid: String): User?
}
