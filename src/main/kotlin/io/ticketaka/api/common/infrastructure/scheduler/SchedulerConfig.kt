package io.ticketaka.api.common.infrastructure.scheduler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
class SchedulerConfig {
    @Bean
    fun taskScheduler(): TaskScheduler {
        return ThreadPoolTaskScheduler().apply {
            poolSize = 10
            setThreadNamePrefix("scheduled-task-")
        }
    }
}
