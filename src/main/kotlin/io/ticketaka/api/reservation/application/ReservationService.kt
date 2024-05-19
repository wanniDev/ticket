package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenUserService: TokenUserService,
    private val concertSeatService: ConcertSeatService,
    private val asyncReservationService: AsyncReservationService,
    private val reservationRepository: ReservationRepository,
) {
    @Transactional
    fun createReservation(command: CreateReservationCommand) {
        val user = tokenUserService.getUser(command.userTsid)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.getAvailableSeats(command.date, command.seatNumbers)
        seats.forEach { seat ->
            seat.reserve()
        }
        asyncReservationService.createReservationAsync(user.getId(), concert.getId(), seats)
    }

    @Transactional
    fun confirmReservation(
        userTsid: String,
        reservationTsid: String,
    ) {
        val user = tokenUserService.getUser(userTsid)
        user.validatePoint()
        val reservation =
            reservationRepository.findByTsid(reservationTsid)
                ?: throw NotFoundException("예약을 찾을 수 없습니다.")
        reservation.validateUser(user)
        reservation.validatePending()
        val reservationSeats = reservation.seats
        reservationSeats.forEach { reservationSeat ->
            val seat = reservationSeat.seat
            seat.validateReserved()
        }
        asyncReservationService.confirmReservationAsync(reservation, user)
    }
}
