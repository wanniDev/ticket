package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaConcertRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConcertRepositoryComposition(
    private val jpaConcertRepository: JpaConcertRepository
): ConcertRepository {
    override fun findByTsid(tsid: String): Concert? {
        return jpaConcertRepository.findByTsid(tsid)
    }

    override fun findByDate(date: LocalDate): Concert? {
        return jpaConcertRepository.findByDate(date)
    }

    override fun findAll(): List<Concert> {
        return jpaConcertRepository.findAll()
    }
}