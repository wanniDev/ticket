package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaUserRepository
import io.ticketaka.api.common.exception.NotFoundException
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryComposition(private val jpaUserRepository: JpaUserRepository): UserRepository {
    override fun findByTsid(userTsid: String): User {
        return jpaUserRepository.findByTsid(userTsid) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
    }
}