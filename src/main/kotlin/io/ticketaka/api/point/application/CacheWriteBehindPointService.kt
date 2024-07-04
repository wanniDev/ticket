package io.ticketaka.api.point.application

import java.math.BigDecimal

interface CacheWriteBehindPointService {
    fun recharge(
        pointId: Long,
        amount: BigDecimal,
    )
}
