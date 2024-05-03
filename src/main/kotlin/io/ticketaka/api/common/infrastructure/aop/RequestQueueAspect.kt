package io.ticketaka.api.common.infrastructure.aop

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.common.infrastructure.jwt.JwtTokenParser
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class RequestQueueAspect(
    private val jwtTokenParser: JwtTokenParser,
    private val tokenWaitingQueue: TokenWaitingQueue,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(io.ticketaka.api.common.infrastructure.aop.OnQueue) || @within(io.ticketaka.api.common.infrastructure.aop.OnQueue)",
    )
    fun onQueuePointcut() {
    }

    @Around("onQueuePointcut()")
    fun beforeOnQueueAdvice(joinPoint: ProceedingJoinPoint) {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val authorizationHeader =
            requestAttributes?.request?.getHeader(
                "tokenTsid",
            ) ?: throw IllegalArgumentException("Authorization header is not found")

        val firstToken = tokenWaitingQueue.peek()

        if (firstToken == null || firstToken.tsid != authorizationHeader) {
            val errorMessage = "현재 대기 중인 토큰과 요청한 토큰이 일치하지 않습니다."
            logger.error(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        joinPoint.proceed()

        tokenWaitingQueue.poll()
    }
}
