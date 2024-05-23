package io.ticketaka.api.user.infrastructure.schelduler

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TokenScheduler(
    private val tokenWaitingMap: TokenWaitingMap,
) {
    private val logger = LoggerFactory.getLogger(TokenScheduler::class.java)

    @Scheduled(fixedDelay = 1000 * 60)
    fun checkExpiredToken() {
        val tokens = tokenWaitingMap.findAll()
        tokens.forEach { token ->
            if (token.isExpired()) {
                tokenWaitingMap.remove(token.tsid)
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    fun activateToken() {
        val tokens = tokenWaitingMap.findAll().sortedBy { it.issuedTime }.take(10)

        tokens.forEach { token ->
            logger.debug("scan tokens for activate, {}:{}", token.tsid, token.status)
            if (token.isExpired()) {
                tokenWaitingMap.remove(token.tsid)
            } else {
                token.activate()
                tokenWaitingMap.put(token.tsid, token)
            }
        }
    }
}
