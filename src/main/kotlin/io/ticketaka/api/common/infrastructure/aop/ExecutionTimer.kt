package io.ticketaka.api.common.infrastructure.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
@Profile("dev")
class ExecutionTimer {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)",
    )
    fun timerPointcut() {
    }

    @Around("timerPointcut()")
    fun assumeExecutionTime(joinPoint: ProceedingJoinPoint) {
        val stopWatch = StopWatch()

        stopWatch.start()
        joinPoint.proceed()
        stopWatch.stop()

        logger.info("Execution time: ${stopWatch.totalTimeMillis} ms")
    }
}
