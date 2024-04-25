package io.ticketaka.api.reservation.application.dto

import java.time.LocalDate

data class CreateReservationCommand(
    val userTsid: String,
    val date: LocalDate,
    val seatNumber: List<String>,
)
