package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface ConcertSeatUpdater {
    fun reserve(
        concertId: Long,
        date: LocalDate,
        seatNumbers: List<String>,
    ): Set<Seat>

    fun confirm(
        concertId: Long,
        date: String,
        seatNumbers: List<String>,
    )
}
