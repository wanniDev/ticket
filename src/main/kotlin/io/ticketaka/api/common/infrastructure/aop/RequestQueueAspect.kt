package io.ticketaka.api.common.infrastructure.aop

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.user.domain.Token
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
class RequestQueueAspect(
    private val tokenWaitingMap: TokenWaitingMap,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(io.ticketaka.api.common.infrastructure.aop.OnQueue) || @within(io.ticketaka.api.common.infrastructure.aop.OnQueue)",
    )
    fun onQueuePointcut() {
    }

    @Around("onQueuePointcut()")
    fun beforeOnQueueAdvice(joinPoint: ProceedingJoinPoint): Any? {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val authorizationHeader =
            requestAttributes?.request?.getHeader(
                "tokenTsid",
            ) ?: throw IllegalArgumentException("요청 헤더에 토큰이 존재하지 않습니다.")

        val tokenFromQueue = tokenWaitingMap.get(authorizationHeader) ?: throw IllegalArgumentException("대기 중인 토큰이 존재하지 않습니다.")

        validateTokenIdentifier(tokenFromQueue, authorizationHeader)

        validateTokenExpiration(tokenFromQueue)

        try {
            return joinPoint.proceed()
        } finally {
            tokenWaitingMap.remove(authorizationHeader)
        }
    }

    private fun validateTokenExpiration(firstToken: Token) {
        val expiredTime = firstToken.issuedTime.plusMinutes(30)
        val now = LocalDateTime.now()
        if (expiredTime.isBefore(now)) {
            val errorMessage = "토큰이 만료되었습니다."
            logger.error(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }

    private fun validateTokenIdentifier(
        firstToken: Token,
        authorizationHeader: String,
    ) {
        if (firstToken.tsid != authorizationHeader) {
            val errorMessage = "현재 대기 중인 토큰과 요청한 토큰이 일치하지 않습니다."
            logger.error(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
    }
}
