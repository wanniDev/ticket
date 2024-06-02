package io.ticketaka.api.point.infrastructure.event

import io.ticketaka.api.point.application.PointService
import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.domain.PointRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

@Component
class PointRechargeEventConsumer(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
    private val pointService: PointService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val eventQueue = LinkedBlockingDeque<PointRechargeEvent>()

    init {
        startEventConsumer()
    }

    fun consume(events: MutableList<PointRechargeEvent>) {
        val pointHistories = mutableListOf<PointHistory>()
        events.forEach { event ->
            val pointHistory =
                PointHistory.newInstance(
                    userId = event.userId,
                    pointId = event.pointId,
                    amount = event.amount,
                    transactionType = PointHistory.TransactionType.RECHARGE,
                )
            pointHistories.add(pointHistory)

            pointService.updateRecharge(event)
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
                    consume(events)
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
