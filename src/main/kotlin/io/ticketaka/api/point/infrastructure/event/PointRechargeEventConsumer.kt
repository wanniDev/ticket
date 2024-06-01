package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import io.ticketaka.api.point.domain.PointRechargeEvent
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

@Component
class PointRechargeEventConsumer(
    private val pointHistoryRepository: PointHistoryRepository,
) {
    private val eventQueue = ConcurrentLinkedQueue<PointRechargeEvent>()

    init {
        startEventConsumer()
    }

    private fun consume(events: MutableList<PointRechargeEvent>) {
        val pointHistories = mutableListOf<PointHistory>()
        events.forEach { event ->
            PointHistory.newInstance(
                userId = event.userId,
                pointId = event.pointId,
                amount = event.amount,
                transactionType = PointHistory.TransactionType.RECHARGE,
            ).let { pointHistories.add(it) }
        }
        pointHistoryRepository.saveAll(pointHistories)
    }

    fun offer(event: PointRechargeEvent) {
        eventQueue.add(event)
    }

    private fun startEventConsumer() {
        thread(
            start = true,
            isDaemon = true,
            name = "PointRechargeEventConsumer",
        ) {
            while (true) {
                if (eventQueue.isNotEmpty()) {
                    val events = mutableListOf<PointRechargeEvent>()
                    var quantity = 5
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