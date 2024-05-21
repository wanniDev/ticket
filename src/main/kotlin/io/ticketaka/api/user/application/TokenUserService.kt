package io.ticketaka.api.user.application

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenUserService(
    private val tokenWaitingQueue: TokenWaitingQueue,
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun createToken(userTsId: String): String {
        if (tokenWaitingQueue.size() > 500L) {
            throw IllegalArgumentException("대기 중인 토큰이 500개를 초과하였습니다.")
        }

        val user = userRepository.findByTsid(userTsId) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")

        val token = Token.newInstance(user.getId())
        token.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        return token.tsid
    }

    @Cacheable(value = ["user"], key = "#tsId")
    @Transactional(readOnly = true)
    fun getUser(tsId: String): User {
        return userRepository.findByTsid(tsId) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
    }

    fun peekToken(tokenId: String): Boolean {
        val queueSize = tokenWaitingQueue.size()
        if (queueSize > 500L) {
            throw IllegalArgumentException("대기 중인 토큰이 500개를 초과하였습니다.")
        }
        tokenWaitingQueue.peek().let { token ->
            if (token == null) {
                throw NotFoundException("토큰을 찾을 수 없습니다.")
            }
            return token.tsid == tokenId
        }
    }
}
