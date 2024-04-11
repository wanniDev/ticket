package io.ticketaka.api.reservation.domain

interface ReservationRepository {
    fun save(reservation: Reservation): Reservation
}