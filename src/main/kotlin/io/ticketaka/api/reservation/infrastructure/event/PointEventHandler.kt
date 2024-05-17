package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import io.ticketaka.api.reservation.domain.point.PointRechargeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointEventHandler(
    private val pointHistoryRepository: PointHistoryRepository,
) {
    @EventListener
    fun handle(event: PointRechargeEvent) {
        val pointHistory =
            PointHistory.newInstance(
                userId = event.userId,
                pointId = event.pointId,
                amount = event.amount,
                transactionType = PointHistory.TransactionType.RECHARGE,
            )

        pointHistoryRepository.save(pointHistory)
    }
}
