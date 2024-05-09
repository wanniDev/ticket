package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface JpaConcertRepository : JpaRepository<Concert, String> {
    fun findByTsid(tsid: String): Concert?

    @Query("SELECT c.id FROM Concert c WHERE c.date = :date")
    fun findIdByDate(date: LocalDate): Long?

    fun findByDate(date: LocalDate): Concert?
}
