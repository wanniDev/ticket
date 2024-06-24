package io.ticketaka.api.point.application

import io.ticketaka.api.common.domain.DomainEvent
import io.ticketaka.api.point.domain.DBPointManager
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.infrastructure.event.AsyncEventLogAppender
import org.springframework.stereotype.Component

@Component
class PointEventService(
    private val asyncEventLogAppender: AsyncEventLogAppender,
    private val dbPointManager: DBPointManager,
    private val pointHistoryRepository: PointHistoryRepository,
) {
    private val warningForRetry = "Retry on failure."
    private val retryFailed = "Retry failed."

    fun appendEventLogInfo(event: DomainEvent) {
        asyncEventLogAppender.appendInfo(event)
    }

    fun recordPointHistory(
        event: DomainEvent,
        transactionType: PointHistory.TransactionType,
    ) {
        when (event) {
            is PointRechargeEvent -> {
                PointHistory
                    .newInstance(
                        userId = event.userId,
                        pointId = event.pointId,
                        amount = event.amount,
                        transactionType = PointHistory.TransactionType.RECHARGE,
                    ).let { pointHistoryRepository.save(it) }
            }
            is PointChargeEvent -> {
                PointHistory
                    .newInstance(
                        userId = event.userId,
                        pointId = event.pointId,
                        amount = event.amount,
                        transactionType = PointHistory.TransactionType.RECHARGE,
                    ).let { pointHistoryRepository.save(it) }
            }
        }
    }

    fun retryableRechargePoint(
        event: PointRechargeEvent,
        retryCount: Int = 3,
    ) {
        try {
            dbPointManager.recharge(event)
        } catch (e: Exception) {
            if (retryCount > 0) {
                asyncEventLogAppender.appendWarning(event, warningForRetry)
                retryableRechargePoint(event, retryCount - 1)
            } else {
                asyncEventLogAppender.appendError(event, retryFailed)
            }
        }
    }

    fun retryableChargePoint(
        event: PointChargeEvent,
        retryCount: Int = 3,
    ) {
        try {
            dbPointManager.charge(event)
        } catch (e: Exception) {
            if (retryCount > 0) {
                asyncEventLogAppender.appendWarning(event, warningForRetry)
                retryableChargePoint(event, retryCount - 1)
            } else {
                asyncEventLogAppender.appendError(event, retryFailed)
            }
        }
    }
}
