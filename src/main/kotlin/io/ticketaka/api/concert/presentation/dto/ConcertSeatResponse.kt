package io.ticketaka.api.concert.presentation.dto

import io.ticketaka.api.concert.application.dto.SeatResult
import java.time.LocalDate

data class ConcertSeatResponse(
    val date: LocalDate,
    val seats: List<SeatResult>,
)
