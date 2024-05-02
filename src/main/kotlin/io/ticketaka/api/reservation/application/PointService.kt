package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointService(
    private val pointHistoryRepository: PointHistoryRepository,
) {
    @Transactional
    fun recordRechargePointHistory(
        userTsid: String,
        userPointTsid: String,
    ) {
        val pointHistory =
            PointHistory.newInstance(
                userTsid = userTsid,
                pointTsid = userPointTsid,
                transactionType = PointHistory.TransactionType.RECHARGE,
            )
        pointHistoryRepository.save(pointHistory)
    }

    fun recordReservationPointHistory(
        tsid: String,
        userPointTsid: String,
    ) {
        val pointHistory =
            PointHistory.newInstance(
                userTsid = tsid,
                pointTsid = userPointTsid,
                transactionType = PointHistory.TransactionType.CHARGE,
            )
        pointHistoryRepository.save(pointHistory)
    }
}
