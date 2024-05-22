package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.reservation.domain.point.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class PointService(
    private val pointRepository: PointRepository,
) {
    fun rollbackPoint(
        pointId: Long,
        amount: BigDecimal,
    ) {
        val point = pointRepository.findById(pointId) ?: throw IllegalArgumentException("포인트를 찾을 수 없습니다.")
        point.rollback(amount)
    }

    @Transactional
    fun getPointForUpdate(pointTsid: String): Point {
        return pointRepository.findByTsidForUpdate(pointTsid) ?: throw IllegalArgumentException("포인트를 찾을 수 없습니다.")
    }
}
