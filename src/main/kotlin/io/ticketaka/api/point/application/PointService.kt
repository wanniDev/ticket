package io.ticketaka.api.point.application

import io.ticketaka.api.point.application.dto.BalanceQueryModel
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.point.domain.PointBalanceUpdater
import io.ticketaka.api.point.domain.PointRechargeEvent
import io.ticketaka.api.user.application.TokenUserQueryService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointService(
    private val tokenUserQueryService: TokenUserQueryService,
    private val pointQueryService: PointQueryService,
    private val pointBalanceUpdater: PointBalanceUpdater,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Async
    @Retryable(retryFor = [Exception::class], backoff = Backoff(delay = 1000, multiplier = 2.0, maxDelay = 10000))
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = tokenUserQueryService.getUser(rechargeCommand.userId)
        val userPoint = pointQueryService.getPoint(user.pointId)
        pointBalanceUpdater.recharge(userPoint, rechargeCommand.amount)
        applicationEventPublisher.publishEvent(PointRechargeEvent(user.id, userPoint.id, rechargeCommand.amount))
    }

    fun getBalance(userId: Long): BalanceQueryModel {
        val user = tokenUserQueryService.getUser(userId)
        val point = pointQueryService.getPoint(user.pointId)
        return BalanceQueryModel(user.id, point.balance)
    }
}
