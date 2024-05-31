package io.ticketaka.api.user.infrastructure.schelduler

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TokenScheduler(
    private val tokenWaitingMap: TokenWaitingMap,
) {
    private val logger = LoggerFactory.getLogger(TokenScheduler::class.java)

    @Value("\${token.capacity}")
    private lateinit var tokenCapacity: String

    @Scheduled(fixedDelay = 1000 * 60)
    fun checkExpiredToken() {
        val tokens = tokenWaitingMap.findAll()
        tokens.forEach { token ->
            if (token.isExpired()) {
                tokenWaitingMap.remove(token.tsid)
            }
        }
    }

    @Scheduled(fixedDelay = 1000 * 5)
    fun activateToken() {
        val tokens = tokenWaitingMap.findAll()
        if (tokens.isEmpty()) {
            return
        }

        tokens.sortedBy { it.issuedTime }.take(tokenCapacity.toInt())
        tokens.forEach { token ->
            logger.debug("scan tokens for activate, {}:{}", token.tsid, token.status)
            if (token.isExpired()) {
                tokenWaitingMap.remove(token.tsid)
            }
        }
    }
}
