package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface SeatRepository {
    fun findByTsid(tsid: String): Seat?

    fun findByConcertId(concertId: Long): List<Seat>

    fun findSeatsByConcertDateAndNumberIn(
        date: LocalDate,
        numbers: List<String>,
    ): Set<Seat>

    fun findConcertDateByStatus(status: Seat.Status): Set<LocalDate>
}
