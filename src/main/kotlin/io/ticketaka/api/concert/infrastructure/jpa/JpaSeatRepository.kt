package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.Seat
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface JpaSeatRepository : JpaRepository<Seat, String> {
    fun findByTsid(tsid: String): Seat?

    fun findByConcertId(concertId: Long): List<Seat>

    fun findByTsidAndConcert(
        tsid: String,
        concert: Concert,
    ): Seat?

    fun findSeatsByConcertDateAndNumberIn(
        date: LocalDate,
        seatNumbers: List<String>,
    ): List<Seat>

    @Query("SELECT DISTINCT s.concert.date FROM Seat s WHERE s.status = :status")
    fun findConcertDateByStatus(status: Seat.Status): Set<LocalDate>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findSeatsByConcertDateAndNumberInOrderByNumber(
        date: LocalDate,
        seatNumbers: List<String>,
    ): List<Seat>
}
