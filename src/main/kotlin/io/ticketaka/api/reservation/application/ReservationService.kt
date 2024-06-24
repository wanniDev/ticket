package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.application.ConcertCacheAsideQueryService
import io.ticketaka.api.concert.domain.ConcertSeatUpdater
import io.ticketaka.api.concert.infrastructure.event.DBSeatStatusManger
import io.ticketaka.api.reservation.application.dto.CreateReservationCommand
import io.ticketaka.api.reservation.domain.reservation.Reservation
import io.ticketaka.api.reservation.domain.reservation.ReservationCreateEvent
import io.ticketaka.api.reservation.domain.reservation.ReservationRepository
import io.ticketaka.api.reservation.domain.reservation.ReservationSeatAllocator
import io.ticketaka.api.user.application.QueueTokenUserCacheAsideQueryService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService(
    private val queueTokenUserCacheAsideQueryService: QueueTokenUserCacheAsideQueryService,
    private val concertCacheAsideQueryService: ConcertCacheAsideQueryService,
    private val reservationRepository: ReservationRepository,
    private val reservationSeatAllocator: ReservationSeatAllocator,
    private val seatStatusManger: DBSeatStatusManger,
    private val concertSeatUpdater: ConcertSeatUpdater,
    private val eventBroker: EventBroker,
) {
    fun createReservation(command: CreateReservationCommand) {
        val user = queueTokenUserCacheAsideQueryService.getUser(command.userId)
        val concert = concertCacheAsideQueryService.getConcert(command.date)
        val seats = concertSeatUpdater.reserve(concert.id, command.date, command.seatNumbers)
        eventBroker.produce(ReservationCreateEvent(user.id, concert.id, seats.map { it.id }))
    }

    @Transactional
    fun reserveFromEvent(event: ReservationCreateEvent) {
        seatStatusManger.reserve(event.seatIds)
        val reservation =
            reservationRepository.save(
                Reservation.createPendingReservation(
                    userId = event.userId,
                    concertId = event.concertId,
                ),
            )
        reservationSeatAllocator.allocate(
            reservationId = reservation.id,
            seatIds = event.seatIds,
        )
    }

    @Async
    @Transactional
    fun confirmReservation(
        userId: Long,
        reservationId: Long,
    ) {
        val user = queueTokenUserCacheAsideQueryService.getUser(userId)
        val reservation =
            reservationRepository.findById(reservationId)
                ?: throw NotFoundException("예약을 찾을 수 없습니다.")
        reservation.validateUser(user)
        reservation.validatePending()
        reservation.confirm()
    }
}
