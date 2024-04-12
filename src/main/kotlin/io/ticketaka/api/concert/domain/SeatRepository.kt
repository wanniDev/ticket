package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface SeatRepository {
    fun findByTsid(tsid: String): Seat?
    fun findByConcertId(concertId: Long): List<Seat>
    fun findByNumberAndConcert(tsid: String, concert: Concert): Seat
    fun findConcertDateByStatus(status: Seat.Status): Set<LocalDate>
}