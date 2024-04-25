package io.ticketaka.api.reservation.domain.reservation

interface ReservationRepository {
    fun save(reservation: Reservation): Reservation
}
