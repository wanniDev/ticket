package io.ticketaka.api.point.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.user.domain.User
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

    fun getPoint(user: User): Point {
        val userPoint = user.point ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        return pointRepository.findById(userPoint.id) ?: throw IllegalArgumentException("포인트를 찾을 수 없습니다.")
    }
}
