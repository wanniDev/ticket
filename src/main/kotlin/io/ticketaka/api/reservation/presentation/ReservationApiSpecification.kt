package io.ticketaka.api.reservation.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Reservation", description = "예약 도메인 API")
interface ReservationApiSpecification {
    @Operation(
        summary = "예약 생성 API",
        description = "예약을 생성하는 API를 제공합니다."
    )
    @ApiResponse(responseCode = "200", description = "예약 생성 성공")
    fun createReservation(request: CreateReservationRequest): CreateReservationResponse
}