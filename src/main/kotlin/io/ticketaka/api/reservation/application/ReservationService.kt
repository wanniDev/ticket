package io.ticketaka.api.reservation.application

import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.application.dto.CreateReservationResult
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenUserService: TokenUserService,
    private val concertSeatService: ConcertSeatService,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createReservation(command: CreateReservationCommand): CreateReservationResult {
        val concert = concertSeatService.getAvailableConcert(command.date, command.seatNumber)
        val seat = concertSeatService.getAvailableSeat(command.date, command.seatNumber)
        val user = tokenUserService.getUser(command.userTsid)

        val reservation = Reservation.createPendingReservation(user, concert, seat)
        user.chargePoint(concert.price)
        reservation.confirm()
        reservationRepository.save(reservation)

        return CreateReservationResult(
            "reservationId",
            reservation.status,
            LocalDateTime.now().plusMinutes(5L)
        )
    }
}