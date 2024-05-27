package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.reservation.application.dto.BalanceQueryModel
import io.ticketaka.api.reservation.application.dto.RechargeCommand
import io.ticketaka.api.reservation.domain.point.PointRechargeEvent
import io.ticketaka.api.user.application.TokenUserQueryService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointBalanceService(
    private val tokenUserQueryService: TokenUserQueryService,
    private val pointService: PointService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Async
    @Retryable(retryFor = [Exception::class], backoff = Backoff(delay = 1000, multiplier = 2.0, maxDelay = 10000))
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val userPoint = pointService.getPointForUpdate(rechargeCommand.pointTsid)
        val user = tokenUserQueryService.getUser(rechargeCommand.userTsid)
        // 실제로는 PG 승인 요청을 수행하는 로직이 들어가야 함
        val amount = rechargeCommand.amount
        userPoint.recharge(user, amount)
        applicationEventPublisher.publishEvent(PointRechargeEvent(user, userPoint, amount))
    }

    fun getBalance(userTsid: String): BalanceQueryModel {
        val user = tokenUserQueryService.getUser(userTsid)
        val point = user.point ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        return BalanceQueryModel(user.tsid, point.balance)
    }
}
