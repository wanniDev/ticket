package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.common.infrastructure.event.EventConsumer
import io.ticketaka.api.point.domain.PointChargeEvent
import org.springframework.stereotype.Component
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

@Component
class PointChargeEventQueue(
    private val eventConsumer: EventConsumer,
    private val asyncEventLogAppender: AsyncEventLogAppender,
) {
    private val eventQueue = LinkedBlockingQueue<PointChargeEvent>()
    private val warningForOffer = "Offer failed."

    init {
        startEventQueue()
    }

    fun offer(event: PointChargeEvent) {
        if (!eventQueue.offer(event, 1000, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            asyncEventLogAppender.appendError(event, warningForOffer)
        }
    }

    private fun startEventQueue() {
        thread(
            start = true,
            isDaemon = true,
            name = "PointChargeEventConsumer",
        ) {
            while (true) {
                if (eventQueue.isNotEmpty()) {
                    val events = mutableListOf<PointChargeEvent>()
                    var quantity = 1000
                    while (eventQueue.isNotEmpty().and(quantity > 0)) {
                        quantity--
                        eventQueue.poll()?.let { events.add(it) }
                    }
                    eventConsumer.consume(events)
                    Thread.sleep(1000)
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
