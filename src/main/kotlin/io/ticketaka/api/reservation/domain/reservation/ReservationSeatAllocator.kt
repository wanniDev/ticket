package io.ticketaka.api.reservation.domain.reservation

interface ReservationSeatAllocator {
    fun allocate(
        reservationId: Long,
        seatIds: List<Long>,
    )
}
