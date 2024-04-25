package io.ticketaka.api.concert.application

import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `find available date by status`() {
        // given
        val mockSeatRepository =
            mock<SeatRepository> {
                on { findConcertDateByStatus(Seat.Status.AVAILABLE) } doReturn setOf(LocalDate.of(2024, 4, 1))
            }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mock())

        // when
        val result = concertSeatService.getDates()

        // then
        assertEquals(listOf(LocalDate.of(2024, 4, 1)), result)
    }

    @Test
    fun `if there is no dates available will return empty collection`() {
        // given
        val mockSeatRepository =
            mock<SeatRepository> {
                on { findConcertDateByStatus(Seat.Status.AVAILABLE) } doReturn emptySet()
            }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mock())

        // when
        val result = concertSeatService.getDates()

        // then
        assertEquals(emptyList<LocalDate>(), result)
    }

    @Test
    fun `find available seats by date`() {
        // given
        val date = LocalDate.of(2024, 4, 1)
        val concert = Concert("concertDateTsid", date)
        concert.id = 1L
        val seatNumber = "1"
        val mockSeatRepository =
            mock<SeatRepository> {
                on { findByConcertId(any()) } doReturn
                    listOf(
                        Seat(
                            "tsid",
                            seatNumber,
                            Seat.Status.AVAILABLE,
                            1000.toBigDecimal(),
                            concert,
                        ),
                    )
            }
        val mockConcertRepository =
            mock<ConcertRepository> {
                on { findByDate(date) } doReturn concert
            }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mockConcertRepository)

        // when
        val result = concertSeatService.getSeatNumbers(date)

        // then
        assertEquals(listOf(seatNumber), result)
    }

    @Test
    fun `if there is no seats available will return empty collection`() {
        // given
        val date = LocalDate.of(2024, 4, 1)
        val concert = Concert("concertDateTsid", date)
        concert.id = 1L
        val mockSeatRepository =
            mock<SeatRepository> {
                on { findByConcertId(any()) } doReturn emptyList()
            }
        val mockConcertRepository =
            mock<ConcertRepository> {
                on { findByDate(date) } doReturn concert
            }
        val concertSeatService = ConcertSeatService(mockSeatRepository, mockConcertRepository)

        // when
        val result = concertSeatService.getSeatNumbers(date)

        // then
        assertEquals(emptyList<Int>(), result)
    }
}
