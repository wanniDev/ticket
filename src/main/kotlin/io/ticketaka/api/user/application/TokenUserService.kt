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
        val user = tokenUserQueryService.getUser(userTsId)

        val token = Token.newInstance(user.getId())
        token.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        return token.tsid
    }

    fun peekToken(tokenTsid: String): Boolean {
        val tokenFromMap = tokenWaitingMap.get(tokenTsid)

        if (tokenFromMap == null || tokenFromMap.isExpired() || tokenFromMap.isDeactivated()) {
            return false
        }
        if (tokenFromMap.isExpired()) {
            throw IllegalStateException("만료된 토큰입니다.")
        }
        val queueSize = tokenWaitingMap.size()
        if (queueSize > 1000) {
            return false
        }
        return tokenFromMap.status == Token.Status.ACTIVE
    }
}
