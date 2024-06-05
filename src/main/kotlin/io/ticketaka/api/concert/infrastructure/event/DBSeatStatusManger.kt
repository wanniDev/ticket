package io.ticketaka.api.concert.infrastructure.event

import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DBSeatStatusManger(
    private val seatRepository: SeatRepository,
) {
    @Transactional
    fun reserve(seatIds: List<Long>) {
        val seats = seatRepository.findByIdsOrderByNumberForUpdate(seatIds)
        seats.forEach { it.reserve() }
    }
}
