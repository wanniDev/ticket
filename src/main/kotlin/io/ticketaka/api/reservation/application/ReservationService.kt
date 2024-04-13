package io.ticketaka.api.reservation.application

import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.application.dto.CreateReservationResult
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserService
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
        val user = tokenUserService.getUser(command.userTsid)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.getAvailableSeats(command.date, command.seatNumber)
        val reservation = reservationRepository.save(Reservation.createPendingReservation(user, concert))
        seats.forEach { seat ->
            user.chargePoint(seat.price)
        }
        reservation.allocate(seats)
        reservation.confirm()

        return CreateReservationResult(
            "reservationId",
            reservation.status,
            LocalDateTime.now().plusMinutes(5L)
        )
    }
}