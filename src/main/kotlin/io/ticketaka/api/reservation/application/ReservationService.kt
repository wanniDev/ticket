package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertSeatService
import io.ticketaka.api.concert.domain.ConcertSeatUpdater
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val tokenUserCacheAsideQueryService: TokenUserCacheAsideQueryService,
    private val concertSeatService: ConcertSeatService,
    private val reservationRepository: ReservationRepository,
    private val concertSeatUpdater: ConcertSeatUpdater,
    private val eventBroker: EventBroker,
) {
    fun createReservation(command: CreateReservationCommand) {
        val user = tokenUserCacheAsideQueryService.getUser(command.userId)
        val concert = concertSeatService.getAvailableConcert(command.date)
        val seats = concertSeatUpdater.reserve(concert.id, command.date, command.seatNumbers)
        eventBroker.produce(ReservationCreateEvent(user.id, concert.id, seats.map { it.id }))
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
