package io.ticketaka.api.concert.infrastructure.jpa

import io.ticketaka.api.concert.domain.Concert
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface JpaConcertRepository: JpaRepository<Concert, String>{
    fun findByTsid(tsid: String): Concert?
    fun findByDate(date: LocalDate): Concert?
}