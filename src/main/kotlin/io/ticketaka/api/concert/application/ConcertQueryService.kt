package io.ticketaka.api.concert.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertQueryService(
    private val concertRepository: ConcertRepository,
    private val seatRepository: SeatRepository,
) {
    @Cacheable(value = ["concert"], key = "#date")
    fun getConcert(date: LocalDate): Concert {
        return concertRepository.findByDate(date)
            ?: throw BadClientRequestException("해당 날짜의 콘서트가 없습니다.")
    }

    @Cacheable(value = ["seatNumbers"], key = "#concertId")
    fun getConcertSeatNumbers(concertId: Long): Set<Seat> {
        return seatRepository.findByConcertId(concertId)
    }
}
