package io.ticketaka.api.reservation.infrastructure.event

import io.ticketaka.api.reservation.domain.point.PointChargeEvent
import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import io.ticketaka.api.reservation.domain.point.PointRechargeEvent
import io.ticketaka.api.reservation.domain.point.PointRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointEventHandler(
    private val pointRepository: PointRepository,
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

    @EventListener
    fun handle(event: PointChargeEvent) {
        pointRepository.updateBalance(event.pointId, event.balance)

        val pointHistory =
            PointHistory.newInstance(
                userId = event.userId,
                pointId = event.pointId,
                amount = event.amount,
                transactionType = PointHistory.TransactionType.CHARGE,
            )

        pointHistoryRepository.save(pointHistory)
    }
}
