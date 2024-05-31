package io.ticketaka.api.common.infrastructure.async

import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
@EnableRetry
class AsyncConfig : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 209
        executor.maxPoolSize = 209
        executor.queueCapacity = 418
        executor.setThreadNamePrefix("Ticketaka-")
        executor.initialize()
        return executor
    }
}
