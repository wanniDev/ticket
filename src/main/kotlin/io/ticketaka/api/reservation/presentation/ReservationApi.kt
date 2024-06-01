package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.common.infrastructure.aop.OnMap
import io.ticketaka.api.reservation.application.ReservationService
import io.ticketaka.api.reservation.presentation.dto.ConfirmReservationRequest
import io.ticketaka.api.reservation.presentation.dto.CreateReservationRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ReservationApi(
    private val reservationService: ReservationService,
) : ReservationApiSpecification {
    @OnMap
    @PostMapping
    override fun createReservation(
        @RequestBody request: CreateReservationRequest,
    ): ResponseEntity<Void> {
        reservationService.createReservation(request.toCommand())
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/confirm")
    override fun confirmReservation(
        @RequestBody request: ConfirmReservationRequest,
    ): ResponseEntity<Void> {
        reservationService.confirmReservation(request.userTsid, request.reservationId)
        return ResponseEntity.noContent().build()
    }
}
