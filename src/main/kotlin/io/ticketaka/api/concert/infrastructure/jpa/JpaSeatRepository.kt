package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Seat
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.time.LocalDate

interface JpaSeatRepository : JpaRepository<Seat, Long> {
    fun findByConcertId(concertId: Long): Set<Seat>

    fun findSeatsByConcertDateAndNumberIn(
        date: LocalDate,
        seatNumbers: List<String>,
    ): List<Seat>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdInOrderByNumber(ids: List<Long>): List<Seat>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findSeatsByConcertDateAndNumberInOrderByNumber(
        date: LocalDate,
        seatNumbers: List<String>,
    ): List<Seat>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findSeatsByConcertIdAndNumberInOrderByNumber(
        concertId: Long,
        seatNumbers: List<String>,
    ): List<Seat>
}
