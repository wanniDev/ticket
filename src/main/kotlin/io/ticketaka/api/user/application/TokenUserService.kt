package io.ticketaka.api.user.application

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.Token
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class TokenUserService(
    private val tokenWaitingMap: TokenWaitingMap,
    private val tokenUserQueryService: TokenUserQueryService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun createToken(userTsId: String): String {
        if (tokenWaitingMap.size() > 500L) {
            throw IllegalArgumentException("대기 중인 토큰이 500개를 초과하였습니다.")
        }

        val user = tokenUserQueryService.getUser(userTsId)

        val token = Token.newInstance(user.getId())
        token.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        return token.tsid
    }

    fun peekToken(tokenTsid: String): Boolean {
        if (tokenWaitingMap.get(tokenTsid) == null) {
            return false
        }
        val queueSize = tokenWaitingMap.size()
        return queueSize < 500L
    }
}
