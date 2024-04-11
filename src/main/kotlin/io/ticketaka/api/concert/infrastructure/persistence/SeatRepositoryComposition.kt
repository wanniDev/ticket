package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaSeatRepository
import org.springframework.stereotype.Repository

@Repository
class SeatRepositoryComposition(
    private val jpaSeatRepository: JpaSeatRepository
): SeatRepository {
    override fun findByTsid(tsid: String): Seat? {
        return jpaSeatRepository.findByTsid(tsid)
    }

    override fun findByConcertId(concertDateId: Long): List<Seat> {
        return jpaSeatRepository.findByConcertId(concertDateId)
    }

    override fun findByNumberAndConcert(tsid: String, concert: Concert): Seat? {
        return jpaSeatRepository.findByTsidAndConcert(tsid, concert)
    }
}