package io.ticketaka.api.point.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.application.dto.BalanceQueryModel
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.PointBalanceCacheUpdater
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointService(
    private val tokenUserCacheAsideQueryService: TokenUserCacheAsideQueryService,
    private val pointCacheAsideQueryService: PointCacheAsideQueryService,
    private val pointBalanceCacheUpdater: PointBalanceCacheUpdater,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val pointRepository: PointRepository,
) {
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = tokenUserCacheAsideQueryService.getUser(rechargeCommand.userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        pointBalanceCacheUpdater.recharge(point.id, rechargeCommand.amount)
        applicationEventPublisher.publishEvent(PointRechargeEvent(user.id, point.id, rechargeCommand.amount))
    }

    fun getBalance(userId: Long): BalanceQueryModel {
        val user = tokenUserCacheAsideQueryService.getUser(userId)
        val point = pointCacheAsideQueryService.getPoint(user.pointId)
        return BalanceQueryModel(user.id, point.balance)
    }

    @Transactional
    fun updateRecharge(event: PointRechargeEvent) {
        val point = pointRepository.findById(event.pointId) ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        point.recharge(event.amount)
        pointRepository.updateBalance(point.id, point.balance)
    }
}
