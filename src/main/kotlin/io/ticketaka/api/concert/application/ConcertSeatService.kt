package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.application.dto.SeatResult
import io.ticketaka.api.concert.domain.ConcertRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertSeatService(
    private val concertCacheAsideQueryService: ConcertCacheAsideQueryService,
    private val concertRepository: ConcertRepository,
) {
    fun getSeatNumbers(date: LocalDate): List<SeatResult> {
        val concert = concertCacheAsideQueryService.getConcert(date)
        return concertCacheAsideQueryService.getConcertSeatNumbers(concert.id).map { SeatResult(it.number, it.status) }
    }

    fun getDates(): List<LocalDate> {
        return concertRepository.findAllDate().sorted()
    }
}
