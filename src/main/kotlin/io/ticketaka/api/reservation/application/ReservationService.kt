package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.infrastructure.cache.ConcertSeatCacheRefresher
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatAllocator
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReservationService(
    private val tokenUserCacheAsideQueryService: TokenUserCacheAsideQueryService,
    private val concertSeatService: ConcertSeatService,
    private val reservationRepository: ReservationRepository,
    private val reservationSeatAllocator: ReservationSeatAllocator,
    private val concertSeatCacheRefresher: ConcertSeatCacheRefresher,
) {
    @Async
    @Transactional
    fun createReservation(command: CreateReservationCommand) {
        val user = tokenUserCacheAsideQueryService.getUser(command.userId)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatService.reserveSeat(concert.id, command.seatNumbers)
        val reservation = reservationRepository.save(Reservation.createPendingReservation(user.id, concert.id))
        reservationSeatAllocator.allocate(reservation.id, seats.map { it.id })
        concertSeatCacheRefresher.refresh(concert.id)
    }

    @Async
    @Transactional
    fun confirmReservation(
        userId: Long,
        reservationId: Long,
    ) {
        val user = tokenUserCacheAsideQueryService.getUser(userId)
        val reservation =
            reservationRepository.findById(reservationId)
                ?: throw NotFoundException("예약을 찾을 수 없습니다.")
        reservation.validateUser(user)
        reservation.validatePending()
        reservation.confirm()
    }
}
