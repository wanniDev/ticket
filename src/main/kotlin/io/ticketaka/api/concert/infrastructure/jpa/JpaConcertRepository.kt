package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface JpaConcertRepository : JpaRepository<Concert, Long> {
    fun findByDate(date: LocalDate): Concert?

    @Query("SELECT c.date FROM Concert c")
    fun findConcertsDate(): Set<LocalDate>
}
