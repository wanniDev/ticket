package io.ticketaka.api.concert.infrastructure.persistence

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.infrastructure.jpa.JpaConcertRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConcertRepositoryComposition(
    private val jpaConcertRepository: JpaConcertRepository
): ConcertRepository {
    override fun findByTsid(tsid: String): Concert {
        return jpaConcertRepository.findByTsid(tsid) ?: throw NotFoundException("콘서트를 찾을 수 없습니다.")
    }

    override fun findByDate(date: LocalDate): Concert {
        return jpaConcertRepository.findByDate(date) ?: throw NotFoundException("콘서트를 찾을 수 없습니다.")
    }

    override fun findAll(): List<Concert> {
        return jpaConcertRepository.findAll()
    }
}