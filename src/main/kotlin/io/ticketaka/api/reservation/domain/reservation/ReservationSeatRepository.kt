package io.ticketaka.api.reservation.domain.reservation

interface ReservationSeatRepository {
    fun saveAll(reservationSeats: List<ReservationSeat>)
}
