package io.ticketaka.api.common.infrastructure.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch

// @Aspect
// @Component
// @Profile("dev")
class ExecutionTimer {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Pointcut(
        "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)",
    )
    fun timerPointcut() {
    }

    @Around("timerPointcut()")
    fun assumeExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        val stopWatch = StopWatch()

        stopWatch.start()
        val result = joinPoint.proceed()
        stopWatch.stop()

        logger.info("Execution time: ${stopWatch.totalTimeMillis} ms")
        return result
    }
}
