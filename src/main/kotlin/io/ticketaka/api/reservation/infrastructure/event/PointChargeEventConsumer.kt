package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.reservation.domain.point.PointChargeEvent
import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

@Component
class PointChargeEventConsumer(
    private val pointHistoryRepository: PointHistoryRepository,
) {
    private val eventQueue = ConcurrentLinkedQueue<PointChargeEvent>()

    init {
        startEventConsumer()
    }

    fun consume(events: MutableList<PointChargeEvent>) {
        val pointHistories = mutableListOf<PointHistory>()
        events.forEach { event ->
            PointHistory.newInstance(
                userId = event.userId,
                pointId = event.pointId,
                amount = event.amount,
                transactionType = PointHistory.TransactionType.CHARGE,
            ).let { pointHistories.add(it) }
        }
        pointHistoryRepository.saveAll(pointHistories)
    }

    fun pile(event: PointChargeEvent) {
        eventQueue.add(event)
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
                    while (eventQueue.isNotEmpty()) {
                        eventQueue.poll()?.let { events.add(it) }
                    }
                    consume(events)
                } else {
                    Thread.sleep(5000)
                }
            }
        }
    }
}
