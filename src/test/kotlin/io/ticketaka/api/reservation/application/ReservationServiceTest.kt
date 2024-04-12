package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import java.time.LocalDate
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {
    @Test
    fun `create reservation`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val user = User.newInstance(point)
        val concert = Concert.newInstance(1000.toBigDecimal() ,date)
        val seat = Seat.newInstance(seatNumber, concert)

        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doReturn concert
        }
        val mockSeatRepository = mock<SeatRepository> {
            on { findByNumberAndConcert(any(), any()) } doReturn seat
        }
        val mockReservationRepository = mock<ReservationRepository>() {
            on { save(any()) } doReturn Reservation.createPendingReservation(user, concert, seat)
        }

        val mockUserService = mock<TokenUserService> {
            on { getUser(any()) } doReturn user
        }

        val reservationService = ReservationService(
            mockUserService, ConcertSeatService(mockSeatRepository, mockConcertRepository), mockReservationRepository)

        // when
        val result = reservationService.createReservation(
            CreateReservationCommand("concertDateTsid", date, seatNumber))

        // then
        assertNotNull(result.reservationId)
        assertEquals(Reservation.Status.CONFIRMED, result.status)
        assertNotNull(result.expiration)
    }

    @Test
    fun `if user try to hava a reservation with occupied seat then throw exception`() {
        // given
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val concert = Concert.newInstance(1000.toBigDecimal() ,date)
        val seat = Seat.newInstance(seatNumber, concert)
        seat.occupy()

        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doReturn concert
        }
        val mockSeatRepository = mock<SeatRepository> {
            on { findByNumberAndConcert(any(), any()) } doReturn seat
        }
        val mockReservationRepository = mock<ReservationRepository>()

        val mockUserService = mock<TokenUserService>()

        val reservationService = ReservationService(
            mockUserService, ConcertSeatService(mockSeatRepository, mockConcertRepository), mockReservationRepository)

        // when
        val exception = assertFailsWith<BadClientRequestException> {
            reservationService.createReservation(
                CreateReservationCommand("concertDateTsid", date, seatNumber))
        }

        // then
        assertEquals("이미 예약된 좌석입니다.", exception.message)
    }

    @Test
    fun `if user try to hava a reservation with not exist concert then throw exception`() {
        // given
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val concert = Concert.newInstance(1000.toBigDecimal() ,date)
        val seat = Seat.newInstance(seatNumber, concert)
        seat.occupy()

        val notFoundConcertErrorMessage = "콘서트를 찾을 수 없습니다."
        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doThrow NotFoundException(notFoundConcertErrorMessage)
        }
        val mockSeatRepository = mock<SeatRepository> ()
        val mockReservationRepository = mock<ReservationRepository>()

        val mockUserService = mock<TokenUserService>()

        val reservationService = ReservationService(
            mockUserService, ConcertSeatService(mockSeatRepository, mockConcertRepository), mockReservationRepository)

        // when
        val exception = assertFailsWith<NotFoundException> {
            reservationService.createReservation(
                CreateReservationCommand("concertDateTsid", date, seatNumber))
        }

        // then
        assertEquals(notFoundConcertErrorMessage, exception.message)
    }

    @Test
    fun `if user try to have a reservation with not enough balance will throw BadClientException`() {
        // given
        val point = Point.newInstance()
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val user = User.newInstance(point)
        val concert = Concert.newInstance(1000.toBigDecimal() ,date)
        val seat = Seat.newInstance(seatNumber, concert)

        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doReturn concert
        }
        val mockSeatRepository = mock<SeatRepository> {
            on { findByNumberAndConcert(any(), any()) } doReturn seat
        }
        val mockReservationRepository = mock<ReservationRepository>()

        val mockUserService = mock<TokenUserService> {
            on { getUser(any()) } doReturn user
        }

        val reservationService = ReservationService(
            mockUserService, ConcertSeatService(mockSeatRepository, mockConcertRepository), mockReservationRepository)

        // when
        val exception = assertFailsWith<BadClientRequestException> {
            reservationService.createReservation(
                CreateReservationCommand("concertDateTsid", date, seatNumber))
        }

        // then
        assertEquals("잔액이 부족합니다.", exception.message)
    }

    @Test
    fun `if user try to have a reservation with negative price will throw BadClientException`() {
        // given
        val point = Point.newInstance()
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val user = User.newInstance(point)
        val concert = Concert.newInstance((-1).toBigDecimal(),date)
        val seat = Seat.newInstance(seatNumber, concert)

        val mockConcertRepository = mock<ConcertRepository> {
            on { findByDate(date) } doReturn concert
        }
        val mockSeatRepository = mock<SeatRepository> {
            on { findByNumberAndConcert(any(), any()) } doReturn seat
        }
        val mockReservationRepository = mock<ReservationRepository>()

        val mockUserService = mock<TokenUserService> {
            on { getUser(any()) } doReturn user
        }

        val reservationService = ReservationService(
            mockUserService, ConcertSeatService(mockSeatRepository, mockConcertRepository), mockReservationRepository)

        // when
        val exception = assertFailsWith<BadClientRequestException> {
            reservationService.createReservation(
                CreateReservationCommand("concertDateTsid", date, seatNumber))
        }

        // then
        assertEquals("결제 금액은 0보다 커야 합니다.", exception.message)
    }
}