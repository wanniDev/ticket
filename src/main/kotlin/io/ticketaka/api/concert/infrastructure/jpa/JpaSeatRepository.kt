package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.ConcertDate
import io.ticketaka.api.concert.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface JpaSeatRepository: JpaRepository<Seat, String>{
    fun findByTsid(tsid: String): Seat?
    fun findByConcertDateId(concertDateId: Long): List<Seat>
    fun findByTsidAndConcertDate(tsid: String, concertDate: ConcertDate): Seat?
}