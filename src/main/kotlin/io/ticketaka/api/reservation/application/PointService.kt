package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import io.ticketaka.api.reservation.domain.point.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class PointService(
    private val pointRepository: PointRepository,
    private val pointHistoryRepository: PointHistoryRepository,
) {
    @Transactional
    fun recordRechargePointHistory(
        userId: Long,
        userPointId: Long,
        amount: BigDecimal,
    ) {
        val pointHistory =
            PointHistory.newInstance(
                userId = userId,
                pointId = userPointId,
                amount = amount,
                transactionType = PointHistory.TransactionType.RECHARGE,
            )
        pointHistoryRepository.save(pointHistory)
    }

    fun recordReservationPointHistory(
        userId: Long,
        userPointId: Long,
        amount: BigDecimal,
    ) {
        val pointHistory =
            PointHistory.newInstance(
                userId = userId,
                pointId = userPointId,
                amount = amount,
                transactionType = PointHistory.TransactionType.CHARGE,
            )
        pointHistoryRepository.save(pointHistory)
    }

    fun getPoint(pointTsid: String): Point {
        return pointRepository.findByTsid(pointTsid) ?: throw IllegalArgumentException("포인트를 찾을 수 없습니다.")
    }
}
