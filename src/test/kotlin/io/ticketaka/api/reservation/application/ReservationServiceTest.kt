package io.ticketaka.api.reservation.application

import io.ticketaka.api.concert.domain.ConcertDate
import io.ticketaka.api.concert.domain.ConcertDateRepository
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.Reservation
import io.ticketaka.api.reservation.domain.ReservationRepository
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {
    @Test
    fun `create reservation`() {
        // given
        val point = Point.newInstance()
        val date = LocalDate.of(2024, 4, 10)
        val seatNumber = "A24"
        val user = User.newInstance(point)
        val concertDate = ConcertDate.newInstance(date)
        val seat = Seat.newInstance(seatNumber, concertDate)

        val mockUserRepository = mock<UserRepository> {
            on { findByTsid(any()) } doReturn user
        }
        val mockConcertDateRepository = mock<ConcertDateRepository> {
            on { findByDate(date) } doReturn concertDate
        }
        val mockSeatRepository = mock<SeatRepository> {
            on { findByNumberAndConcertDate(any(), any()) } doReturn seat
        }
        val mockReservationRepository = mock<ReservationRepository>() {
            on { save(any()) } doReturn Reservation.createPendingReservation(user, seat)
        }

        val reservationService = ReservationService(mockUserRepository, mockConcertDateRepository, mockSeatRepository, mockReservationRepository)

        // when
        val result = reservationService.createReservation(
            CreateReservationCommand("concertDateTsid", date, seatNumber))

        // then
        assertNotNull(result.reservationId)
        assertEquals(Reservation.Status.PENDING, result.status)
        assertNotNull(result.expiration)
    }
}