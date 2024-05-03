package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaReservationRepository
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryComposition(
    private val jpaReservationRepository: JpaReservationRepository,
) : ReservationRepository {
    override fun save(reservation: Reservation): Reservation {
        val saved = jpaReservationRepository.save(reservation)
        return saved
    }

    override fun findByTsid(reservationTsid: String): Reservation? {
        return jpaReservationRepository.findByTsid(reservationTsid)
    }
}
