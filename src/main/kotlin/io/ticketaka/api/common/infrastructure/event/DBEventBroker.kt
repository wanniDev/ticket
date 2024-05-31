package io.ticketaka.api.common.infrastructure.event

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.common.domain.EventBroker
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

@Component
class DBEventBroker(
    private val eventDispatcher: EventDispatcher,
) : EventBroker {
    private val eventQueue = ConcurrentLinkedQueue<DomainEvent>()

    init {
        startEventBroker()
    }

    override fun produce(domainEvent: DomainEvent) {
        eventQueue.add(domainEvent)
    }

    private fun startEventBroker() {
        thread(
            start = true,
            isDaemon = true,
            name = "DBEventBroker",
        ) {
            while (true) {
                if (eventQueue.isNotEmpty()) {
                    val events = mutableListOf<DomainEvent>()
                    while (eventQueue.isNotEmpty()) {
                        eventQueue.poll()?.let { events.add(it) }
                    }
                    eventDispatcher.dispatch(events)
                    eventDispatcher.consume(events)
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
