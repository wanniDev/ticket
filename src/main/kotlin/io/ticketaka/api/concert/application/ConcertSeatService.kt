package io.ticketaka.api.concert.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ConcertSeatService(
    private val seatRepository: SeatRepository,
    private val concertRepository: ConcertRepository,
) {
    fun getDates(): List<LocalDate> {
        return seatRepository.findConcertDateByStatus(Seat.Status.AVAILABLE).sorted()
    }

    fun getSeatNumbers(date: LocalDate): List<SeatResult> {
        val concertId =
            concertRepository.findIdByDate(date)
                ?: throw BadClientRequestException("해당 날짜의 콘서트가 없습니다.")
        return seatRepository.findByConcertId(concertId)
            .map { SeatResult(it.number, it.status) }
    }

    fun getAvailableConcert(date: LocalDate): Concert {
        val concert =
            concertRepository.findByDate(date)
                ?: throw BadClientRequestException("해당 날짜의 콘서트가 없습니다.")
        return concert
    }

    fun getAvailableSeats(
        date: LocalDate,
        seatNumbers: List<String>,
    ): Set<Seat> {
        val concert =
            concertRepository.findByDate(date)
                ?: throw BadClientRequestException("해당 날짜의 콘서트가 없습니다.")
        val seats = seatRepository.findSeatsByConcertDateAndNumberInOrderByNumber(concert.date, seatNumbers)
        seats.forEach { seat ->
            if (seat.status != Seat.Status.AVAILABLE) {
                throw BadClientRequestException("이미 예약된 좌석입니다.")
            }
        }
        return seats
    }
}
