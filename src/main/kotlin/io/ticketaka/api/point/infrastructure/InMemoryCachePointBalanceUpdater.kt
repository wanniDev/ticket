package io.ticketaka.api.point.infrastructure

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
        point: Point,
        amount: BigDecimal,
    ) {
        val cache = caffeineCacheManager.getCache("point") ?: throw IllegalStateException("point 캐시가 존재하지 않습니다.")
        point.recharge(amount)
        synchronized(this) {
            cache.put(point.id, point)
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
