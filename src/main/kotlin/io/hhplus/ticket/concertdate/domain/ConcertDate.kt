package io.hhplus.ticket.concertdate.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class ConcertDate(
    @Id
    val id: String,
    val date: LocalDateTime
)