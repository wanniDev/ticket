package io.ticketaka.api.concert.presentation

import io.ticketaka.api.concert.presentation.dto.ConcertDateResponse
import io.ticketaka.api.concert.presentation.dto.ConcertSeatResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/concert")
class ConcertApi : ConcertApiSpecification {
    @GetMapping("/date")
    override fun getConcertDate(): ResponseEntity<ConcertDateResponse> {
        return ResponseEntity.ok(
            ConcertDateResponse(
                listOf(
                    LocalDate.of(2024, 4, 1),
                    LocalDate.of(2024, 4, 2),
                    LocalDate.of(2024, 4, 3),
                    LocalDate.of(2024, 4, 4),
                )
            )
        )
    }

    @GetMapping("/seat")
    override fun getConcertSeat(@RequestParam date: LocalDate): ResponseEntity<ConcertSeatResponse> {
        return ResponseEntity.ok(
            ConcertSeatResponse(
                date,
                listOf(1,2,3,4)
            )
        )
    }
}