package io.ticketaka.api.concert.domain

interface SeatRepository {
    fun findByTsid(tsid: String): Seat?
    fun findByConcertId(concertId: Long): List<Seat>

    fun findByNumberAndConcert(tsid: String, concert: Concert): Seat?
}