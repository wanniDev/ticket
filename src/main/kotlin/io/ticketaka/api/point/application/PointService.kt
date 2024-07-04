package io.ticketaka.api.point.application

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.point.application.dto.BalanceQueryModel
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.user.application.QueueTokenUserCacheAsideQueryService
import org.springframework.stereotype.Service

@Service
class PointService(
    private val queueTokenUserCacheAsideQueryService: QueueTokenUserCacheAsideQueryService,
    private val pointCacheAsideQueryService: PointCacheAsideQueryService,
    private val cacheWriteBehindPointService: CacheWriteBehindPointService,
    private val eventBroker: EventBroker,
) {
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = queueTokenUserCacheAsideQueryService.getUser(rechargeCommand.userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        cacheWriteBehindPointService.recharge(point.id, rechargeCommand.amount)
        eventBroker.produce(PointRechargeEvent(user.id, point.id, rechargeCommand.amount))
    }

    fun getBalance(userId: Long): BalanceQueryModel {
        val user = queueTokenUserCacheAsideQueryService.getUser(userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        return BalanceQueryModel(user.id, point.balance)
    }
}
