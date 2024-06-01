package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaUserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryComposition(private val jpaUserRepository: JpaUserRepository) : UserRepository {
    override fun findById(id: Long): User? {
        return jpaUserRepository.findById(id).orElse(null)
    }
}
