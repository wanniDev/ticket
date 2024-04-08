package io.ticketaka.api.concert.presentation.dto

import java.time.LocalDate

data class ConcertSeatResponse(
    val date: LocalDate,
    val seats: List<Int>
)
