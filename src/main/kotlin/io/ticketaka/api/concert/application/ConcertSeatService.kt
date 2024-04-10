package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.ConcertDateRepository
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ConcertSeatService(
    private val seatRepository: SeatRepository,
    private val concertDateRepository: ConcertDateRepository
) {
    fun getSeats(date: LocalDate): List<Int> {
        val concertDate = concertDateRepository.findByDate(date)
        return seatRepository.findByConcertDateId(concertDate.id!!).map { it.number.toInt() }
    }
}