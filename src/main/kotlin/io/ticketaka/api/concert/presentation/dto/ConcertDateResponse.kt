package io.ticketaka.api.concert.presentation.dto

import java.time.LocalDate

data class ConcertDateResponse(
    val dates: List<LocalDate>,
)
