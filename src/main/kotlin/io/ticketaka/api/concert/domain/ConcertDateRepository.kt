package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface ConcertDateRepository {
    fun findByTsid(tsid: String): ConcertDate?
    fun findByDate(date: LocalDate): ConcertDate?
}