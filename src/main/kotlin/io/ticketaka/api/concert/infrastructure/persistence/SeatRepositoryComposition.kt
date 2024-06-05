package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaSeatRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class SeatRepositoryComposition(
    private val jpaSeatRepository: JpaSeatRepository,
) : SeatRepository {
    override fun findById(id: Long): Seat? {
        return jpaSeatRepository.findById(id).orElseThrow { throw NotFoundException("좌석을 찾을 수 없습니다") }
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

    override fun findByIdsOrderByNumberForUpdate(ids: List<Long>): Set<Seat> {
        return jpaSeatRepository.findByIdInOrderByNumber(ids).toSet()
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
}
