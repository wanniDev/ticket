package io.ticketaka.api.concert.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.ticketaka.api.concert.presentation.dto.ConcertDateResponse
import io.ticketaka.api.concert.presentation.dto.ConcertSeatResponse
import org.springframework.http.ResponseEntity
import java.time.LocalDate

@Tag(name = "Concert", description = "콘서트 도메인 API")
interface ConcertApiSpecification {
    @Operation(
        summary = "콘서트 일정 조회 API",
        description = "콘서트 일정을 조회하는 API를 제공합니다.",
    )
    @ApiResponse(responseCode = "200", description = "콘서트 일정 조회 성공")
    fun getConcertDate(): ResponseEntity<ConcertDateResponse>

    @Operation(
        summary = "콘서트 좌석 조회 API",
        description = "콘서트 좌석을 조회하는 API를 제공합니다.",
    )
    @ApiResponse(responseCode = "200", description = "콘서트 좌석 조회 성공")
    fun getConcertSeat(date: LocalDate): ResponseEntity<ConcertSeatResponse>
}
