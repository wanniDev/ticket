package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.ConcertDate
import io.ticketaka.api.concert.domain.ConcertDateRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ConcertSeatServiceTest {
    @Test
    fun `find available seats by date`() {
        // given
        val date = LocalDate.of(2024, 4, 1)
        val concertDate = ConcertDate("concertDateTsid", date)
        concertDate.id = 1L
        val seatNumber = "1"
        val mockSeatRepository = mock<SeatRepository> {
            on { findByConcertDateId(any()) } doReturn listOf(
                Seat(
                    "tsid",
                    seatNumber,
                    Seat.Status.AVAILABLE, concertDate
                )
            )
        }
        val mockConcertDateRepository = mock<ConcertDateRepository> {
            on { findByDate(date) } doReturn concertDate
        }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mockConcertDateRepository)

        // when
        val result = concertSeatService.getSeats(date)

        // then
        assertEquals(listOf(seatNumber.toInt()), result)
    }
}