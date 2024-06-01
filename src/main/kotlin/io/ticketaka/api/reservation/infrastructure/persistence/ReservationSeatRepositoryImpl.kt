package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.reservation.ReservationSeat
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaReservationSeatRepository
import org.springframework.stereotype.Repository

@Repository
class ReservationSeatRepositoryImpl(
    private val jpaReservationSeatRepository: JpaReservationSeatRepository,
) : ReservationSeatRepository {
    override fun saveAll(reservationSeats: List<ReservationSeat>) {
        jpaReservationSeatRepository.saveAll(reservationSeats)
    }
}
