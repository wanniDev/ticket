package io.ticketaka.api.reservation.application.dto

import java.time.LocalDate

data class CreateReservationCommand(
    val userId: Long,
    val date: LocalDate,
    val seatNumbers: List<String>,
)
