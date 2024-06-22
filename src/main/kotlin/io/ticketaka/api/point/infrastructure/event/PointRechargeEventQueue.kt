package io.ticketaka.api.point.infrastructure.event

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
    private val pointHistoryRepository: PointHistoryRepository,
    private val dbPointManager: DBPointManager,
    private val asyncEventLogAppender: AsyncEventLogAppender,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val eventQueue = LinkedBlockingDeque<PointRechargeEvent>()
    private val maxRetries = 3
    private val warningForRetry = "Retry on failure."
    private val retryFailed = "Retry failed."
    private val warningForOffer = "Offer failed."

    init {
        startEventConsumer()
    }

    fun consume(events: List<PointRechargeEvent>) {
        val pointHistories = mutableSetOf<PointHistory>()
        events.forEach { event ->
            asyncEventLogAppender.appendInfo(event)

            PointHistory
                .newInstance(
                    userId = event.userId,
                    pointId = event.pointId,
                    amount = event.amount,
                    transactionType = PointHistory.TransactionType.RECHARGE,
                ).let { pointHistories.add(it) }

            retryOnFailure(event)
        }
        pointHistoryRepository.saveAll(pointHistories.toList())
    }

    private fun retryOnFailure(
        event: PointRechargeEvent,
        retryCount: Int = 0,
    ) {
        try {
            dbPointManager.recharge(event)
        } catch (e: Exception) {
            if (retryCount < maxRetries) {
                asyncEventLogAppender.appendWarning(event, warningForRetry)
                retryOnFailure(event, retryCount + 1)
            } else {
                asyncEventLogAppender.appendError(event, retryFailed)
            }
        }
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
                    consume(events.toList())
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
