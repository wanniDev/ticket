package io.hhplus.ticket.concertdate.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/concert")
class ConcertApi {
    @GetMapping("/date")
    fun getConcertDate(): ResponseEntity<ConcertDateResponse> {
        return ResponseEntity.ok(ConcertDateResponse(
                listOf(
                    LocalDate.of(2024, 4, 1),
                    LocalDate.of(2024, 4, 2),
                    LocalDate.of(2024, 4, 3),
                    LocalDate.of(2024, 4, 4),
                )
            )
        )
    }
}