package io.ticketaka.api.point.domain

import java.math.BigDecimal

interface PointBalanceUpdater {
    fun recharge(
        pointId: Long,
        amount: BigDecimal,
    )

    fun charge(
        point: Point,
        amount: BigDecimal,
    )
}
