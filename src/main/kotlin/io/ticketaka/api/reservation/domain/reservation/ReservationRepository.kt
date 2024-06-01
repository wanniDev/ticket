package io.ticketaka.api.reservation.domain.reservation

interface ReservationRepository {
    fun findById(id: Long): Reservation?

    fun save(reservation: Reservation): Reservation
}
