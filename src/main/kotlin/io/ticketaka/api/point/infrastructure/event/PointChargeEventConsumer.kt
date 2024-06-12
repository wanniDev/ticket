package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.point.domain.DBPointManager
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import org.springframework.stereotype.Component
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

@Component
class PointChargeEventConsumer(
    private val pointHistoryRepository: PointHistoryRepository,
    private val dbPointManger: DBPointManager,
    private val asyncEventLogAppender: AsyncEventLogAppender,
) {
    private val eventQueue = LinkedBlockingQueue<PointChargeEvent>()
    private val maxRetries = 3
    private val warningForRetry = "Retry on failure."
    private val retryFailed = "Retry failed."
    private val warningForOffer = "Offer failed."

    init {
        startEventConsumer()
    }

    fun consume(events: MutableList<PointChargeEvent>) {
        val pointHistories = mutableListOf<PointHistory>()
        events.forEach { event ->
            asyncEventLogAppender.appendInfo(event)

            PointHistory.newInstance(
                userId = event.userId,
                pointId = event.pointId,
                amount = event.amount,
                transactionType = PointHistory.TransactionType.CHARGE,
            ).let { pointHistories.add(it) }

            retryOnFailure(event)
        }
        pointHistoryRepository.saveAll(pointHistories)
    }

    private fun retryOnFailure(
        event: PointChargeEvent,
        retryCount: Int = 0,
    ) {
        try {
            dbPointManger.charge(event)
        } catch (e: Exception) {
            if (retryCount < maxRetries) {
                asyncEventLogAppender.appendWarning(event, warningForRetry)
                retryOnFailure(event, retryCount + 1)
            } else {
                asyncEventLogAppender.appendError(event, retryFailed)
            }
        }
    }

    fun offer(event: PointChargeEvent) {
        if (!eventQueue.offer(event, 1000, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            asyncEventLogAppender.appendError(event, warningForOffer)
        }
    }

    private fun startEventConsumer() {
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
                    consume(events)
                    Thread.sleep(1000)
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
