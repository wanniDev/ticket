package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaSeatRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class SeatRepositoryComposition(
    private val jpaSeatRepository: JpaSeatRepository,
) : SeatRepository {
    override fun findByTsid(tsid: String): Seat? {
        return jpaSeatRepository.findByTsid(tsid)
    }

    override fun findByConcertId(concertId: Long): Set<Seat> {
        return jpaSeatRepository.findByConcertId(concertId)
    }

    override fun findSeatsByConcertDateAndNumberIn(
        date: LocalDate,
        numbers: List<String>,
    ): Set<Seat> {
        return jpaSeatRepository.findSeatsByConcertDateAndNumberIn(date, numbers).toSet()
    }

    override fun findSeatsByConcertDateAndNumberInOrderByNumber(
        date: LocalDate,
        numbers: List<String>,
    ): Set<Seat> {
        return jpaSeatRepository.findSeatsByConcertDateAndNumberInOrderByNumber(date, numbers).toSet()
    }

    override fun findSeatsByConcertIdAndNumberInOrderByNumberForUpdate(
        concertId: Long,
        numbers: List<String>,
    ): Set<Seat> {
        return jpaSeatRepository.findSeatsByConcertIdAndNumberInOrderByNumber(concertId, numbers).toSet()
    }

    override fun findConcertDateByStatus(status: Seat.Status): Set<LocalDate> {
        return jpaSeatRepository.findConcertDateByStatus(status)
    }
}
