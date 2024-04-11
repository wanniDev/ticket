package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.reservation.application.ReservationService
import io.ticketaka.api.reservation.domain.Reservation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/reservation")
class ReservationApi(
    private val reservationService: ReservationService
) : ReservationApiSpecification {
    @PostMapping
    override fun createReservation(@RequestBody request: CreateReservationRequest): CreateReservationResponse {
        val result = reservationService.createReservation(request.toCommand())
        return CreateReservationResponse(
            result.reservationId,
            result.status,
            result.expiration
        )
    }
}