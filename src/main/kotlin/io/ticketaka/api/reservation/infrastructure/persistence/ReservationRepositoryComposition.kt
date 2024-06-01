package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.common.exception.NotFoundException
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

    override fun findById(id: Long): Reservation? {
        return jpaReservationRepository.findById(id).orElseThrow { throw NotFoundException("해당 예약을 찾을 수 없습니다.") }
    }
}
