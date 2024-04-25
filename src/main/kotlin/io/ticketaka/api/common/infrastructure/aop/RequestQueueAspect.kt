package io.ticketaka.api.common.infrastructure.aop

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class RequestQueueAspect(
    private val tokenWaitingQueue: TokenWaitingQueue,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(io.ticketaka.api.common.infrastructure.aop.OnQueue) || @within(io.ticketaka.api.common.infrastructure.aop.OnQueue)",
    )
    fun onQueuePointcut() {
    }

    @Before("onQueuePointcut()")
    fun beforeOnQueueAdvice() {
        logger.info("RequestQueueAspect")
    }
}
