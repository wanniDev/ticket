package io.ticketaka.api.point.domain

interface DBPointManager {
    fun recharge(event: PointRechargeEvent)

    fun charge(event: PointChargeEvent)
}
