package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.BadClientRequestException
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertCacheAsideQueryService
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.ConcertSeatUpdater
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
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
        val user = User.newInstance(point.id)
        val concert = Concert.newInstance(date)
        val seats = setOf(Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert.id))
        val reservation = Reservation.createPendingReservation(user.id, concert.id)

        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }

        val mockConcertSeatCacheAsideQueryService =
            mock<ConcertCacheAsideQueryService> {
                on { getConcert(date) } doReturn concert
            }

        val mockConcertUpdater =
            mock<ConcertSeatUpdater> {
                on { reserve(concert.id, date, seats.map { it.number }.toList()) } doReturn seats
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mockConcertSeatCacheAsideQueryService,
                mock(),
                mockConcertUpdater,
                mock(),
            )

        // when
        reservationService.createReservation(
            CreateReservationCommand(user.id, date, seats.map { it.number }.toList()),
        )

        // then
        assertEquals(Reservation.Status.PENDING, reservation.status)
    }

    @Test
    fun `if user try to hava a reservation with occupied seat then throw exception`() {
        // given
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val concert = Concert.newInstance(date)
        val seat = Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert.id)
        seat.occupy()
        val seats = setOf(seat)

        val point = Point.newInstance(10000.toBigDecimal())
        val user = User.newInstance(point.id)

        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }
        val mockConcertSeatCacheAsideQueryService =
            mock<ConcertCacheAsideQueryService> {
                on { getConcert(date) } doReturn concert
            }

        val mockConcertUpdater =
            mock<ConcertSeatUpdater> {
                on { reserve(concert.id, date, seats.map { it.number }.toList()) } doThrow BadClientRequestException("이미 예약된 좌석입니다.")
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mockConcertSeatCacheAsideQueryService,
                mock(),
                mockConcertUpdater,
                mock(),
            )

        // when
        val exception =
            assertFailsWith<BadClientRequestException> {
                reservationService.createReservation(
                    CreateReservationCommand(user.id, date, listOf(seatNumber)),
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
        val seat = Seat.newInstance(seatNumber, 1000.toBigDecimal(), concert.id)
        seat.occupy()

        val notFoundConcertErrorMessage = "콘서트를 찾을 수 없습니다."

        val mockReservationRepository = mock<ReservationRepository>()

        val mockTokenUserCacheAsideQueryService = mock<TokenUserCacheAsideQueryService>()
        val mockConcertSeatCacheAsideQueryService =
            mock<ConcertCacheAsideQueryService> {
                on { getConcert(date) } doThrow NotFoundException(notFoundConcertErrorMessage)
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mockConcertSeatCacheAsideQueryService,
                mockReservationRepository,
                mock(),
                mock(),
            )

        // when
        val exception =
            assertFailsWith<NotFoundException> {
                reservationService.createReservation(
                    CreateReservationCommand(1L, date, listOf(seatNumber)),
                )
            }

        // then
        assertEquals(notFoundConcertErrorMessage, exception.message)
    }

    @Test
    fun `test reservation confirm`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        val user = User.newInstance(point.id)
        val concert = Concert.newInstance(LocalDate.now())
        val reservation = Reservation.createPendingReservation(user.id, concert.id)
        val seat = Seat.newInstance("A24", 1000.toBigDecimal(), concert.id)
        seat.reserve()
        reservation.allocate(setOf(seat))

        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findById(reservation.id) } doReturn reservation
            }

        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(user.id) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mock(),
                mockReservationRepository,
                mock(),
                mock(),
            )

        // when
        reservationService.confirmReservation(user.id, reservation.id)

        // then
    }

    @Test
    fun `when user try to confirm reservation with not exist reservation then throw exception`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        val user = User.newInstance(point.id)
        val notFoundReservationErrorMessage = "예약을 찾을 수 없습니다."
        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findById(any()) } doThrow NotFoundException(notFoundReservationErrorMessage)
            }
        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(user.id) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mock(),
                mockReservationRepository,
                mock(),
                mock(),
            )

        // when
        val exception =
            assertFailsWith<NotFoundException> {
                reservationService.confirmReservation(user.id, 1L)
            }

        // then
        assertEquals(notFoundReservationErrorMessage, exception.message)
    }

    @Test
    fun `when user try to confirm reservation with not pending status then throw exception`() {
        // given
        val point = Point.newInstance(10000.toBigDecimal())
        val user = User.newInstance(point.id)
        val concert = Concert.newInstance(LocalDate.now())
        val reservation = Reservation.createPendingReservation(user.id, concert.id)
        val seat = Seat.newInstance("A24", 1000.toBigDecimal(), concert.id)
        seat.reserve()
        reservation.allocate(setOf(seat))
        reservation.confirm()

        val mockReservationRepository =
            mock<ReservationRepository> {
                on { findById(reservation.id) } doReturn reservation
            }

        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(user.id) } doReturn user
            }

        val reservationService =
            ReservationService(
                mockTokenUserCacheAsideQueryService,
                mock(),
                mockReservationRepository,
                mock(),
                mock(),
            )

        // when
        val exception =
            assertFailsWith<IllegalStateException> {
                reservationService.confirmReservation(user.id, reservation.id)
            }

        // then
        assertEquals("Reservation is not pending", exception.message)
    }
}
