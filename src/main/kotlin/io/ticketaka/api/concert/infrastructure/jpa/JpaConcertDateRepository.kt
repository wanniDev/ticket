package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.ConcertDate
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface JpaConcertDateRepository: JpaRepository<ConcertDate, String>{
    fun findByTsid(tsid: String): ConcertDate?
    fun findByDate(date: LocalDate): ConcertDate
}