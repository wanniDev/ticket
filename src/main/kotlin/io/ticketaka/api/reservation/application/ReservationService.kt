package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.infrastructure.async.AsyncPostReservationProcessor
import io.ticketaka.api.user.application.TokenUserQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenQueryUserService: TokenUserQueryService,
    private val concertSeatService: ConcertSeatService,
    private val asyncPostReservationProcessor: AsyncPostReservationProcessor,
    private val reservationRepository: ReservationRepository,
) {
    @Transactional
    fun createReservation(command: CreateReservationCommand) {
        val user = tokenQueryUserService.getUser(command.userTsid)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.reserveSeat(concert.getId(), command.seatNumbers)
        asyncPostReservationProcessor.createReservation(user.getId(), concert.getId(), seats)
    }

    @Transactional
    fun confirmReservation(
        userTsid: String,
        reservationTsid: String,
    ) {
        val user = tokenQueryUserService.getUser(userTsid)
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
        asyncPostReservationProcessor.confirmResercation(reservation, user)
    }
}
