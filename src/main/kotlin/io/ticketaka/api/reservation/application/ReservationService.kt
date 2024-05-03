package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.reservation.application.dto.ConfirmReservationResult
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.application.dto.CreateReservationResult
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenUserService: TokenUserService,
    private val concertSeatService: ConcertSeatService,
    private val reservationRepository: ReservationRepository,
    private val pointService: PointService,
) {
    @Transactional
    fun createReservation(command: CreateReservationCommand): CreateReservationResult {
        val user = tokenUserService.getUser(command.userTsid)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.getAvailableSeats(command.date, command.seatNumbers)
        val reservation = reservationRepository.save(Reservation.createPendingReservation(user, concert))
        seats.forEach { seat ->
            seat.reserve()
        }
        reservation.allocate(seats)

        return CreateReservationResult(
            reservation.tsid,
            reservation.status,
            LocalDateTime.now().plusMinutes(5L),
        )
    }

    @Transactional
    fun confirmReservation(
        userTsid: String,
        reservationTsid: String,
    ): ConfirmReservationResult {
        val user = tokenUserService.getUser(userTsid)
        val reservation =
            reservationRepository.findByTsid(reservationTsid)
                ?: throw NotFoundException("예약을 찾을 수 없습니다.")
        reservation.validateUser(user)
        reservation.validatePending()
        val reservationSeats = reservation.seats
        var totalAmount = BigDecimal.ZERO
        reservationSeats.forEach { reservationSeat ->
            val seat = reservationSeat.seat
            seat.validateReserved()
            totalAmount = totalAmount.add(seat.price)
        }
        user.chargePoint(totalAmount)
        reservation.confirm()

        pointService.recordReservationPointHistory(user.getId(), user.point!!.getId(), totalAmount)

        return ConfirmReservationResult(
            reservation.tsid,
            reservation.status,
        )
    }
}
