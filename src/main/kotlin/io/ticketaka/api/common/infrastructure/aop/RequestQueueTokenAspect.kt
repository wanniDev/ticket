package io.ticketaka.api.common.infrastructure.aop

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.token.QueueToken
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime

@Aspect
@Component
class RequestQueueTokenAspect(
    private val tokenWaitingMap: TokenWaitingMap,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(io.ticketaka.api.common.infrastructure.aop.OnMap) || @within(io.ticketaka.api.common.infrastructure.aop.OnMap)",
    )
    fun onQueuePointcut() {
    }

    @Around("onQueuePointcut()")
    fun beforeOnQueueAdvice(joinPoint: ProceedingJoinPoint): Any? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val authorizationHeader =
            requestAttributes?.request?.getHeader(
                "tokenId",
            ) ?: throw IllegalArgumentException("요청 헤더에 토큰이 존재하지 않습니다.")
        val tokenId = authorizationHeader.toLongOrNull() ?: throw IllegalArgumentException("토큰 아이디가 올바르지 않습니다.")

        val tokenFromQueue = tokenWaitingMap.get(tokenId) ?: throw IllegalArgumentException("대기 중인 토큰이 존재하지 않습니다.")

        validateTokenIdentifier(tokenFromQueue, tokenId)

        validateTokenActive(tokenFromQueue)

        validateTokenExpiration(tokenFromQueue)

        try {
            return joinPoint.proceed()
        } finally {
            tokenFromQueue.deactivate()
            tokenWaitingMap.put(tokenId, tokenFromQueue)
        }
    }

    private fun validateTokenActive(firstQueueToken: QueueToken) {
        if (firstQueueToken.status != QueueToken.Status.ACTIVE) {
            val errorMessage = "토큰이 활성화되지 않았습니다."
            logger.debug(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

    private fun validateTokenExpiration(firstQueueToken: QueueToken) {
        val expiredTime = firstQueueToken.issuedTime.plusMinutes(30)
        val now = LocalDateTime.now()
        if (expiredTime.isBefore(now)) {
            val errorMessage = "토큰이 만료되었습니다."
            logger.debug(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

    private fun validateTokenIdentifier(
        firstQueueToken: QueueToken,
        authorizationHeader: Long,
    ) {
        if (firstQueueToken.id != authorizationHeader) {
            val errorMessage = "현재 대기 중인 토큰과 요청한 토큰이 일치하지 않습니다."
            logger.debug(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }
}
