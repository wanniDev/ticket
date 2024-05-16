package io.ticketaka.api.reservation.application

import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.domain.User
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class AsyncReservationService(
    private val reservationRepository: ReservationRepository,
    private val pointService: PointService,
) {
    @Async
    @Transactional(propagation = Propagation.NESTED)
    fun createReservationAsync(
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
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun confirmReservationAsync(
        reservation: Reservation,
        user: User,
    ) {
        val reservationSeats = reservation.seats
        var totalAmount = BigDecimal.ZERO
        reservationSeats.forEach { reservationSeat ->
            val seat = reservationSeat.seat
            totalAmount = totalAmount.add(seat.price)
        }
        user.chargePoint(totalAmount)
        reservation.confirm()

        pointService.recordReservationPointHistory(user.getId(), user.point!!.getId(), totalAmount)
    }
}
