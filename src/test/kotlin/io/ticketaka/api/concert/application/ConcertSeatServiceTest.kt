package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ConcertSeatServiceTest {
    @Test
    fun `find available seats by date`() {
        // given
        val date = LocalDate.of(2024, 4, 1)
        val concert = Concert("concertDateTsid", 1000.toBigDecimal(), date)
        concert.id = 1L
        val seatNumber = "1"
        val mockSeatRepository = mock<SeatRepository> {
            on { findByConcertId(any()) } doReturn listOf(
                Seat(
                    "tsid",
                    seatNumber,
                    Seat.Status.AVAILABLE, concert
                )
            )
        }
        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doReturn concert
        }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mockConcertRepository)

        // when
        val result = concertSeatService.getSeats(date)

        // then
        assertEquals(listOf(seatNumber.toInt()), result)
    }
}