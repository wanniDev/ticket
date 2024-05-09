package io.ticketaka.api.concert.presentation

import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.presentation.dto.ConcertDateResponse
import io.ticketaka.api.concert.presentation.dto.ConcertSeatResponse
import io.ticketaka.api.concert.presentation.dto.SeatResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/concert")
class ConcertApi(private val concertSeatService: ConcertSeatService) : ConcertApiSpecification {
    @GetMapping("/date")
    override fun getConcertDate(): ResponseEntity<ConcertDateResponse> {
        return ResponseEntity.ok(
            ConcertDateResponse(
                concertSeatService.getDates(),
            ),
        )
    }

    @GetMapping("/seat")
    override fun getConcertSeat(
        @RequestParam date: LocalDate,
    ): ResponseEntity<ConcertSeatResponse> {
        val seatResponse =
            concertSeatService.getSeatNumbers(date).map { seatResult ->
                SeatResponse(
                    seatResult.number,
                    seatResult.status,
                )
            }
        return ResponseEntity.ok(
            ConcertSeatResponse(
                date,
                seatResponse,
            ),
        )
    }
}
