package io.ticketaka.api.reservation.presentation

import io.ticketaka.api.common.infrastructure.aop.OnQueue
import io.ticketaka.api.reservation.application.ReservationService
import io.ticketaka.api.reservation.presentation.dto.ConfirmReservationRequest
import io.ticketaka.api.reservation.presentation.dto.ConfirmReservationResponse
import io.ticketaka.api.reservation.presentation.dto.CreateReservationRequest
import io.ticketaka.api.reservation.presentation.dto.CreateReservationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ReservationApi(
    private val reservationService: ReservationService,
) : ReservationApiSpecification {
    @OnQueue
    @PostMapping
    override fun createReservation(
        @RequestBody request: CreateReservationRequest,
    ): CreateReservationResponse {
        val result = reservationService.createReservation(request.toCommand())
        return CreateReservationResponse(
            result.reservationTsid,
            result.status,
            result.expiration,
        )
    }

    @OnQueue
    @PostMapping("/confirm")
    override fun confirmReservation(
        @RequestBody request: ConfirmReservationRequest,
    ): ConfirmReservationResponse {
        val result = reservationService.confirmReservation(request.userTsid, request.reservationTsid)
        return ConfirmReservationResponse(
            result.reservationTsid,
            result.status,
        )
    }
}
