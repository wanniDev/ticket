package io.ticketaka.api.point.infrastructure.persistence

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.DBPointManager
import io.ticketaka.api.point.domain.PointChargeEvent
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.domain.PointRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DBTransactionPointManager(
    private val pointRepository: PointRepository,
) : DBPointManager {
    @Transactional
    override fun recharge(event: PointRechargeEvent) {
        val point = pointRepository.findById(event.pointId) ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        point.recharge(event.amount)
    }

    @Transactional
    override fun charge(event: PointChargeEvent) {
        val point = pointRepository.findById(event.pointId) ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        point.charge(event.amount)
    }
}
