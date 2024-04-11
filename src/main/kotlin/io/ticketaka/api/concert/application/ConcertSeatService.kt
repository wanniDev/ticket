package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ConcertSeatService(
    private val seatRepository: SeatRepository,
    private val concertRepository: ConcertRepository
) {
    fun getSeats(date: LocalDate): List<Int> {
        val concertDate = concertRepository.findByDate(date) ?: throw IllegalArgumentException("Concert date not found")
        return seatRepository.findByConcertId(concertDate.id!!).map { it.number.toInt() }
    }
}