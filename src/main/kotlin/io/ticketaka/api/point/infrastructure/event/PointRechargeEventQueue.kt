package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.common.infrastructure.event.EventConsumer
import io.ticketaka.api.point.domain.DBPointManager
import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import io.ticketaka.api.point.domain.PointRechargeEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

@Component
class PointRechargeEventQueue(
    private val eventConsumer: EventConsumer,
    private val asyncEventLogAppender: AsyncEventLogAppender,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val eventQueue = LinkedBlockingDeque<PointRechargeEvent>()
    private val warningForOffer = "Offer failed."

    init {
        startEventConsumer()
    }

    fun offer(event: PointRechargeEvent) {
        if (!eventQueue.offer(event, 1000, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            asyncEventLogAppender.appendError(event, warningForOffer)
        }
    }

    private fun startEventConsumer() {
        thread(
            start = true,
            isDaemon = true,
            name = "pointEventConsumer",
        ) {
            while (true) {
                val stopWatch = StopWatch()
                stopWatch.start()
                var processingTime = 1000L
                val currentThread = Thread.currentThread()
                while (currentThread.state.name == Thread.State.WAITING.name) {
                    logger.info(currentThread.state.name)
                    Thread.sleep(processingTime)
                }
                if (eventQueue.isNotEmpty()) {
                    val events = mutableListOf<PointRechargeEvent>()
                    var quantity = 5
                    while (eventQueue.isNotEmpty().and(quantity > 0)) {
                        quantity--
                        eventQueue.poll()?.let { events.add(it) }
                    }
                    eventConsumer.consume(events.toList())
                    stopWatch.stop()
                    processingTime = stopWatch.totalTimeMillis
                    logger.debug("PointRechargeEventConsumer consume ${events.size} events, cost ${processingTime}ms")
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
