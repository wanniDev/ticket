package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.reservation.domain.Reservation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/reservation")
class ReservationApi {
    @PostMapping
    fun createReservation(@RequestBody request: CreateReservationRequest): CreateReservationResponse {
        return CreateReservationResponse(
            "reservationId",
            Reservation.Status.CONFIRMED,
            LocalDateTime.now().plusMinutes(5L)
        )
    }
}