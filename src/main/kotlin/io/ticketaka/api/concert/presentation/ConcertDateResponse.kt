package io.ticketaka.api.concert.presentation

import java.time.LocalDate

data class ConcertDateResponse(
    val dates: List<LocalDate>
)
