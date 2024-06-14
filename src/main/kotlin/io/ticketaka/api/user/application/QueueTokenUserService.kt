package io.ticketaka.api.user.application

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.token.QueueToken
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class QueueTokenUserService(
    private val tokenWaitingMap: TokenWaitingMap,
    private val queueTokenUserCacheAsideQueryService: QueueTokenUserCacheAsideQueryService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun createToken(userId: Long): Long {
        val user = queueTokenUserCacheAsideQueryService.getUser(userId)

        val queueToken = QueueToken.newInstance(user.id)
        queueToken.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        return queueToken.id
    }

    fun peekToken(tokenId: Long): Boolean {
        val tokenFromMap = tokenWaitingMap.get(tokenId)

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
        return tokenFromMap.status == QueueToken.Status.ACTIVE
    }
}
