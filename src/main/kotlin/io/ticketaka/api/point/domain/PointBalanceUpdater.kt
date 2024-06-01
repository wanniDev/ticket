package io.ticketaka.api.point.domain

import java.math.BigDecimal

interface PointBalanceUpdater {
    fun recharge(
        point: Point,
        amount: BigDecimal,
    )

    fun charge(
        point: Point,
        amount: BigDecimal,
    )
}
