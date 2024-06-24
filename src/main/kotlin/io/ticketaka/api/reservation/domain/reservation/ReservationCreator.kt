package io.ticketaka.api.reservation.domain.reservation

interface ReservationCreator {
    fun fromEvent(event: ReservationCreateEvent)
}
