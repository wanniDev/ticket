package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaUserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository,
) : UserRepository {
    override fun save(user: User): User = jpaUserRepository.save(user)

    override fun findById(id: Long): User? = jpaUserRepository.findById(id).orElse(null)

    override fun findByEmail(email: String): User? = jpaUserRepository.findByEmail(email)
}
