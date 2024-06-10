package io.ticketaka.api.point.application

import io.ticketaka.api.common.domain.EventBroker
import io.ticketaka.api.point.application.dto.BalanceQueryModel
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.CachePointRecharger
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import org.springframework.stereotype.Service

@Service
class PointService(
    private val tokenUserCacheAsideQueryService: TokenUserCacheAsideQueryService,
    private val pointCacheAsideQueryService: PointCacheAsideQueryService,
    private val cachePointRecharger: CachePointRecharger,
    private val eventBroker: EventBroker,
) {
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = tokenUserCacheAsideQueryService.getUser(rechargeCommand.userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        cachePointRecharger.recharge(point.id, rechargeCommand.amount)
        eventBroker.produce(PointRechargeEvent(user.id, point.id, rechargeCommand.amount))
    }

    fun getBalance(userId: Long): BalanceQueryModel {
        val user = tokenUserCacheAsideQueryService.getUser(userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        return BalanceQueryModel(user.id, point.balance)
    }
}
