package io.ticketaka.api.reservation.infrastructure.async

import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.infrastructure.cache.ConcertSeatCacheRefresher
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.domain.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class AsyncPostReservationProcessor(
    private val reservationRepository: ReservationRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val concertSeatCacheRefresher: ConcertSeatCacheRefresher,
) {
    @Async
    @Transactional(propagation = Propagation.NESTED)
    @Retryable(retryFor = [Exception::class], backoff = Backoff(delay = 1000, multiplier = 2.0, maxDelay = 10000))
    fun createReservation(
        userId: Long,
        concertId: Long,
        seats: Set<Seat>,
    ) {
        try {
            val reservation = reservationRepository.save(Reservation.createPendingReservation(userId, concertId))
            reservation.allocate(seats)
        } catch (e: Exception) {
            seats.forEach { seat ->
                seat.available()
            }
            e.printStackTrace()
        } finally {
            concertSeatCacheRefresher.refresh()
        }
    }

    @Async
    @Transactional(propagation = Propagation.NESTED)
    @Retryable(retryFor = [Exception::class], backoff = Backoff(delay = 1000, multiplier = 2.0, maxDelay = 10000))
    fun confirmResercation(
        reservation: Reservation,
        user: User,
    ) {
        val reservationSeats = reservation.seats
        var totalAmount = BigDecimal.ZERO
        reservationSeats.forEach { reservationSeat ->
            val seat = reservationSeat.seat
            totalAmount = totalAmount.add(seat.price)
        }
        reservation.confirm()
        reservationRepository.save(reservation)

        user.chargePoint(totalAmount)
        val userPoint = user.point ?: throw IllegalArgumentException("포인트를 찾을 수 없습니다.")
        userPoint.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
    }
}
