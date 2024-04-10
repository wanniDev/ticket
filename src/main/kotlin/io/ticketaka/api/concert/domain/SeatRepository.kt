package io.ticketaka.api.concert.domain

interface SeatRepository {
    fun findByTsid(tsid: String): Seat?
    fun findByConcertDateId(concertDateId: Long): List<Seat>
}