package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.reservation.ReservationSeat
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatAllocator
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatRepository
import org.springframework.stereotype.Component

@Component
class ReservationSeatAllocatorImpl(
    private val reservationSeatRepository: ReservationSeatRepository,
) : ReservationSeatAllocator {
    override fun allocate(
        reservationId: Long,
        seatIds: List<Long>,
    ) {
        val reservationSeats =
            seatIds.map { seatId ->
                ReservationSeat.create(
                    reservationId = reservationId,
                    seatId = seatId,
                )
            }
        reservationSeatRepository.saveAll(reservationSeats)
    }
}
