package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.concert.infrastructure.event.DBSeatStatusManger
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationCreator
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatAllocator
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ReservationCreatorTransaction(
    private val reservationRepository: ReservationRepository,
    private val reservationSeatAllocator: ReservationSeatAllocator,
    private val seatStatusManger: DBSeatStatusManger,
) : ReservationCreator {
    @Transactional
    override fun fromEvent(event: ReservationCreateEvent) {
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
