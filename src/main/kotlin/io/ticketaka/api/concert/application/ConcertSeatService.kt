package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.common.exception.BadClientRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ConcertSeatService(
    private val seatRepository: SeatRepository,
    private val concertRepository: ConcertRepository
) {
    fun getDates(): List<LocalDate> {
        return seatRepository.findConcertDateByStatus(Seat.Status.AVAILABLE).sorted()
    }

    fun getSeats(date: LocalDate): List<String> {
        val concertDate = concertRepository.findByDate(date)
        return seatRepository.findByConcertId(concertDate.id!!)
            .filter { it.status == Seat.Status.AVAILABLE }
            .sortedBy { it.number}
            .map { it.number}
    }

    fun getAvailableConcert(date: LocalDate): Concert {
        return concertRepository.findByDate(date)
    }

    fun getAvailableSeat(date: LocalDate, seatNumber: String): Seat {
        val concert = concertRepository.findByDate(date)
        val seat = seatRepository.findByNumberAndConcert(seatNumber, concert)
        if (!seat.isAvailable()) {
            throw BadClientRequestException("이미 예약된 좌석입니다.")
        }
        return seat
    }
}