package io.ticketaka.api.point.infrastructure

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointBalanceUpdater
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class InMemoryCachePointBalanceUpdater(
    private val caffeineCacheManager: CaffeineCacheManager,
) : PointBalanceUpdater {
    override fun recharge(
        pointId: Long,
        amount: BigDecimal,
    ) {
        val cache = caffeineCacheManager.getCache("point") ?: throw NotFoundException("point 캐시가 존재하지 않습니다.")
        synchronized(cache) {
            val point = cache.get(pointId, Point::class.java) ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
            point.recharge(amount)
            cache.put(pointId, point)
        }
    }

    override fun charge(
        point: Point,
        amount: BigDecimal,
    ) {
        val cache = caffeineCacheManager.getCache("point") ?: throw IllegalStateException("point 캐시가 존재하지 않습니다.")
        point.charge(amount)
        synchronized(this) {
            cache.put(point.id, point)
        }
    }
}
