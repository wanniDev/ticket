package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface SeatRepository {
    fun findById(id: Long): Seat?

    fun findByConcertId(concertId: Long): Set<Seat>

    fun findSeatsByConcertDateAndNumberIn(
        date: LocalDate,
        numbers: List<String>,
    ): Set<Seat>

    fun findSeatsByConcertDateAndNumberInOrderByNumber(
        date: LocalDate,
        numbers: List<String>,
    ): Set<Seat>

    fun findSeatsByConcertIdAndNumberInOrderByNumberForUpdate(
        concertId: Long,
        numbers: List<String>,
    ): Set<Seat>
}
