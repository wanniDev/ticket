package io.hhplus.ticket.concertdate.presentation

import java.time.LocalDate

data class ConcertDateResponse(
    val dates: List<LocalDate>
)
