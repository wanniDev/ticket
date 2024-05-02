package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import java.time.LocalDate

data class CreateReservationRequest(
    val userTsid: String,
    val date: LocalDate,
    val seatNumbers: List<String>,
) {
    fun toCommand() =
        CreateReservationCommand(
            userTsid = userTsid,
            date = date,
            seatNumbers = seatNumbers,
        )
}
