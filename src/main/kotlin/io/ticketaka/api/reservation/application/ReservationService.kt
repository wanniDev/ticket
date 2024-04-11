package io.ticketaka.api.reservation.application

import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.SeatRepository
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.application.dto.CreateReservationResult
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val userRepository: UserRepository,
    private val concertRepository: ConcertRepository,
    private val seatRepository: SeatRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun createReservation(command: CreateReservationCommand): CreateReservationResult {
        val user = userRepository.findByTsid(command.userTsid)
            ?: throw IllegalArgumentException("User not found")
        val concert = concertRepository.findByDate(command.date)
            ?: throw IllegalArgumentException("Concert date not found")

        val seat = seatRepository.findByNumberAndConcert(command.seatNumber, concert)
            ?: throw IllegalArgumentException("Seat not found")


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