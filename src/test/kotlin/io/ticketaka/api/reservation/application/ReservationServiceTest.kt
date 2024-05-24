package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.infrastructure.async.AsyncPostReservationProcessor
import io.ticketaka.api.user.application.TokenUserQueryService
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {
    @Test
    fun `create reservation`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        point.id = 1
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val user = User.newInstance(point)
        user.id = 1
        val concert = Concert.newInstance(date)
        concert.id = 1
        val seats = setOf(Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert))

        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(any()) } doReturn user
            }

        val mockAsyncPostReservationProcessor = mock<AsyncPostReservationProcessor>()

        val concertSeatService =
            mock<ConcertSeatService> {
                on { getAvailableConcert(date) } doReturn concert
                on { reserveSeat(concert.getId(), seats.map { it.number }.toList()) } doReturn seats
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                concertSeatService,
                mockAsyncPostReservationProcessor,
                mock(),
            )

        // when
        reservationService.createReservation(
            CreateReservationCommand("concertDateTsid", date, seats.map { it.number }.toList()),
        )

        // then
        verify(mockAsyncPostReservationProcessor).createReservation(user.getId(), concert.getId(), seats)
    }

    @Test
    fun `if user try to hava a reservation with occupied seat then throw exception`() {
        // given
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val concert = Concert.newInstance(date)
        concert.id = 1
        val seat = Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert)
        seat.occupy()
        val seats = setOf(seat)
        val mockReservationRepository = mock<ReservationRepository>()

        val user = User.newInstance(Point.newInstance(10000.toBigDecimal()))
        user.id = 1
        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(any()) } doReturn user
            }
        val concertSeatService =
            mock<ConcertSeatService> {
                on { getAvailableConcert(date) } doReturn concert
                on { reserveSeat(concert.getId(), seats.map { it.number }.toList()) } doThrow BadClientRequestException("이미 예약된 좌석입니다.")
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                concertSeatService,
                mock(),
                mockReservationRepository,
            )

        // when
        val exception =
            assertFailsWith<BadClientRequestException> {
                reservationService.createReservation(
                    CreateReservationCommand("concertDateTsid", date, listOf(seatNumber)),
                )
            }

        // then
        assertEquals("이미 예약된 좌석입니다.", exception.message)
    }

    @Test
    fun `if user try to hava a reservation with not exist concert then throw exception`() {
        // given
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val concert = Concert.newInstance(date)
        val seat = Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert)
        seat.occupy()

        val notFoundConcertErrorMessage = "콘서트를 찾을 수 없습니다."
        val mockConcertRepository =
            mock<ConcertRepository> {
                on { findByDate(date) } doThrow NotFoundException(notFoundConcertErrorMessage)
            }
        val mockSeatRepository = mock<SeatRepository>()
        val mockReservationRepository = mock<ReservationRepository>()

        val mockTokenUserQueryService = mock<TokenUserQueryService>()

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                ConcertSeatService(mock(), mockSeatRepository, mockConcertRepository),
                mock(),
                mockReservationRepository,
            )

        // when
        val exception =
            assertFailsWith<NotFoundException> {
                reservationService.createReservation(
                    CreateReservationCommand("concertDateTsid", date, listOf(seatNumber)),
                )
            }

        // then
        assertEquals(notFoundConcertErrorMessage, exception.message)
    }

    @Test
    fun `test reservation confirm`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        point.id = 1
        val user = User.newInstance(point)
        user.id = 1
        val concert = Concert.newInstance(LocalDate.now())
        concert.id = 1
        val reservation = Reservation.createPendingReservation(user.getId(), concert.getId())
        reservation.id = 1
        val seat = Seat.newInstance("A24", 1000.toBigDecimal(), concert)
        seat.id = 1
        seat.reserve()
        reservation.allocate(setOf(seat))

        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findByTsid(reservation.tsid) } doReturn reservation
            }

        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(user.tsid) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                mock(),
                mock(),
                mockReservationRepository,
            )

        // when
        reservationService.confirmReservation(user.tsid, reservation.tsid)

        // then
    }

    @Test
    fun `when user try to confirm reservation with not exist reservation then throw exception`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        point.id = 1
        val user = User.newInstance(point)
        user.id = 1
        val notFoundReservationErrorMessage = "예약을 찾을 수 없습니다."
        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findByTsid(any()) } doThrow NotFoundException(notFoundReservationErrorMessage)
            }
        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(user.tsid) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                mock(),
                mock(),
                mockReservationRepository,
            )

        // when
        val exception =
            assertFailsWith<NotFoundException> {
                reservationService.confirmReservation(user.tsid, "reservationTsid")
            }

        // then
        assertEquals(notFoundReservationErrorMessage, exception.message)
    }

    @Test
    fun `when user try to confirm reservation with not reserved seat then throw exception`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        point.id = 1
        val user = User.newInstance(point)
        user.id = 1
        val concert = Concert.newInstance(LocalDate.now())
        concert.id = 1
        val reservation = Reservation.createPendingReservation(user.getId(), concert.getId())
        reservation.id = 1
        val seat = Seat.newInstance("A24", 1000.toBigDecimal(), Concert.newInstance(LocalDate.now()))
        seat.id = 1
        reservation.allocate(setOf(seat))

        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findByTsid(reservation.tsid) } doReturn reservation
            }

        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(user.tsid) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                mock(),
                mock(),
                mockReservationRepository,
            )

        // when
        val exception =
            assertFailsWith<IllegalStateException> {
                reservationService.confirmReservation(user.tsid, reservation.tsid)
            }

        // then
        assertEquals("Seat number: ${seat.number} is not reserved", exception.message)
    }

    @Test
    fun `when user try to confirm reservation with not pending status then throw exception`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        point.id = 1
        val user = User.newInstance(point)
        user.id = 1
        val concert = Concert.newInstance(LocalDate.now())
        concert.id = 1
        val reservation = Reservation.createPendingReservation(user.getId(), concert.getId())
        reservation.id = 1
        val seat = Seat.newInstance("A24", 1000.toBigDecimal(), concert)
        seat.id = 1
        seat.reserve()
        reservation.allocate(setOf(seat))
        reservation.confirm()

        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findByTsid(reservation.tsid) } doReturn reservation
            }

        val mockTokenUserQueryService =
            mock<TokenUserQueryService> {
                on { getUser(user.tsid) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserQueryService,
                mock(),
                mock(),
                mockReservationRepository,
            )

        // when
        val exception =
            assertFailsWith<IllegalStateException> {
                reservationService.confirmReservation(user.tsid, reservation.tsid)
            }

        // then
        assertEquals("Reservation is not pending", exception.message)
    }
}
