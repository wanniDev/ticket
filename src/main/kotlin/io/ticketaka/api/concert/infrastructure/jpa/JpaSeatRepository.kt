package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface JpaSeatRepository: JpaRepository<Seat, String>{
    fun findByTsid(tsid: String): Seat?
    fun findByConcertId(concertId: Long): List<Seat>
    fun findByTsidAndConcert(tsid: String, concert: Concert): Seat?
}