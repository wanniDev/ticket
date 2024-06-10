package io.ticketaka.api.point.domain

interface DBPointRecharger {
    fun recharge(event: PointRechargeEvent)
}
