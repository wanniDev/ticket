package io.ticketaka.api.concert.domain

import java.time.LocalDate

interface ConcertRepository {
    fun findById(id: Long): Concert?

    fun findByDate(date: LocalDate): Concert?

    fun findAll(): List<Concert>

    fun findAllDate(): Set<LocalDate>
}
