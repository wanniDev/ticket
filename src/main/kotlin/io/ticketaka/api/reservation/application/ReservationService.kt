package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.infrastructure.cache.ConcertSeatCacheRefresher
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserQueryService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenQueryUserService: TokenUserQueryService,
    private val concertSeatService: ConcertSeatService,
    private val reservationRepository: ReservationRepository,
    private val concertSeatCacheRefresher: ConcertSeatCacheRefresher,
) {
    @Async
    @Transactional
    fun createReservation(command: CreateReservationCommand) {
        val user = tokenQueryUserService.getUser(command.userTsid)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.reserveSeat(concert.id, command.seatNumbers)
        val reservation = reservationRepository.save(Reservation.createPendingReservation(user.getId(), concert.id))
        reservation.allocate(seats)
        concertSeatCacheRefresher.refresh(concert.id)
    }

    @Async
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
        reservation.validateSeatsReserved()
        reservation.confirm()
    }
}
