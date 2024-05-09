package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    fun findByTsid(tsid: String): Concert?

    fun findByDate(date: LocalDate): Concert?

    fun findAll(): List<Concert>
}
