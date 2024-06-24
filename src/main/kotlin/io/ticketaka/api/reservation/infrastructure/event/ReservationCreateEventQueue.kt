package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.common.infrastructure.event.EventConsumer
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

@Component
class ReservationCreateEventQueue(
    private val eventConsumer: EventConsumer,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val eventQueue = LinkedBlockingDeque<ReservationCreateEvent>()

    init {
        startEventConsumer()
    }

    fun offer(event: ReservationCreateEvent) {
        eventQueue.add(event)
    }

    private fun startEventConsumer() {
        thread(
            start = true,
            isDaemon = true,
            name = "reservationEventConsumer",
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
                    val events = mutableListOf<ReservationCreateEvent>()
                    var quantity = 1000
                    while (eventQueue.isNotEmpty().and(quantity > 0)) {
                        quantity--
                        eventQueue.poll()?.let { events.add(it) }
                    }
                    eventConsumer.consume(events)
                    stopWatch.stop()
                    processingTime = stopWatch.totalTimeMillis
                    logger.debug("reservationEventConsumer consume ${events.size} events, cost ${processingTime}ms")
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
