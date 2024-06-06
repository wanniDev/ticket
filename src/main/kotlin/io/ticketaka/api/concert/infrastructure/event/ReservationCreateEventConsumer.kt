package io.ticketaka.api.concert.infrastructure.event

import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatAllocator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

@Component
class ReservationCreateEventConsumer(
    private val reservationRepository: ReservationRepository,
    private val reservationSeatAllocator: ReservationSeatAllocator,
    private val seatStatusManger: DBSeatStatusManger,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val eventQueue = LinkedBlockingDeque<ReservationCreateEvent>()

    init {
        startEventConsumer()
    }

    fun consume(events: MutableList<ReservationCreateEvent>) {
        events.forEach { event ->
            seatStatusManger.reserve(event.seatIds)
            val reservation =
                reservationRepository.save(
                    Reservation.createPendingReservation(
                        userId = event.userId,
                        concertId = event.concertId,
                    ),
                )
            reservationSeatAllocator.allocate(
                reservationId = reservation.id,
                seatIds = event.seatIds,
            )
        }
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
                    consume(events)
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
