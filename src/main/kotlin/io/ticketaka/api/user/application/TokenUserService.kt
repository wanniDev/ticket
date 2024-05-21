package io.ticketaka.api.user.application

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.user.domain.Token
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class TokenUserService(
    private val tokenWaitingQueue: TokenWaitingQueue,
    private val tokenUserQueryService: TokenUserQueryService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun createToken(userTsId: String): String {
        if (tokenWaitingQueue.size() > 500L) {
            throw IllegalArgumentException("대기 중인 토큰이 500개를 초과하였습니다.")
        }

        val user = tokenUserQueryService.getUser(userTsId)

        val token = Token.newInstance(user.getId())
        token.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        return token.tsid
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
