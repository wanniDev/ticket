package io.ticketaka.api.user.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class TokenUserQueryService(
    private val userRepository: UserRepository,
) {
    @Cacheable(value = ["user"], key = "#id")
    fun getUser(id: Long): User {
        return userRepository.findById(id) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
    }
}
