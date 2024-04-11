package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import java.time.LocalDate

data class CreateReservationRequest(
    val usertsid: String,
    val date: LocalDate,
    val seatNumber: String
) {
    fun toCommand() = CreateReservationCommand(
        userTsid = usertsid,
        date = date,
        seatNumber = seatNumber
    )
}