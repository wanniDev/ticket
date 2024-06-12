package io.ticketaka.api.point.infrastructure.event

import com.fasterxml.jackson.databind.ObjectMapper
import io.ticketaka.api.common.domain.DomainEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AsyncEventLogAppender(
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger("event")

    fun appendError(
        event: DomainEvent,
        reason: String = "",
    ) {
        logger.error("${event.javaClass.name} ${objectMapper.writeValueAsString(event)} $reason")
    }

    fun appendInfo(event: DomainEvent) {
        logger.info("${event.javaClass.name} ${objectMapper.writeValueAsString(event)}")
    }

    fun appendWarning(
        event: DomainEvent,
        reason: String = "",
    ) {
        logger.warn("${event.javaClass.name} ${objectMapper.writeValueAsString(event)} $reason")
    }
}
