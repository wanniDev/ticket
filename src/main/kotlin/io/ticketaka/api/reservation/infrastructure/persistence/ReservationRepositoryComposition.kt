package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.Reservation
import io.ticketaka.api.reservation.domain.ReservationRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaReservationRepository
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryComposition(
    private val jpaReservationRepository: JpaReservationRepository
): ReservationRepository {
    override fun save(reservation: Reservation): Reservation {
        return jpaReservationRepository.save(reservation)
    }
}