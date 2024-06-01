package io.ticketaka.api.reservation.presentation.dto

import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import java.time.LocalDate

data class CreateReservationRequest(
    val userId: Long,
    val date: LocalDate,
    val seatNumbers: List<String>,
) {
    fun toCommand() =
        CreateReservationCommand(
            userId = userId,
            date = date,
            seatNumbers = seatNumbers,
        )
}
