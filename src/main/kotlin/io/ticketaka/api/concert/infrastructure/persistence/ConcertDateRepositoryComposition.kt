package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.concert.domain.ConcertDate
import io.ticketaka.api.concert.domain.ConcertDateRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaConcertDateRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConcertDateRepositoryComposition(
    private val jpaConcertDateRepository: JpaConcertDateRepository
): ConcertDateRepository {
    override fun findByTsid(tsid: String): ConcertDate? {
        return jpaConcertDateRepository.findByTsid(tsid)
    }

    override fun findByDate(date: LocalDate): ConcertDate? {
        return jpaConcertDateRepository.findByDate(date)
    }
}