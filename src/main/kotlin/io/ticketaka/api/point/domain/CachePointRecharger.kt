package io.ticketaka.api.point.domain

import java.math.BigDecimal

interface CachePointRecharger {
    fun recharge(
        pointId: Long,
        amount: BigDecimal,
    )
}
