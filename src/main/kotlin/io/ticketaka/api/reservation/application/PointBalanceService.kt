package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.reservation.application.dto.BalanceQueryModel
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.application.dto.RechargeCommand
import io.ticketaka.api.user.application.TokenUserQueryService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointBalanceService(
    private val tokenUserQueryService: TokenUserQueryService,
    private val paymentService: PaymentService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = tokenUserQueryService.getUser(rechargeCommand.userTsid)
        val userId = user.getId()
        val userPoint = user.point ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        // 실제로는 PG 승인 요청을 수행하는 로직이 들어가야 함
        val amount = rechargeCommand.amount
        paymentService.paymentApprovalAsync(
            PaymentCommand(
                userId = userId,
                pointId = userPoint.getId(),
                amount = amount,
            ),
        )

        user.rechargePoint(amount)
        userPoint.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
    }

    fun getBalance(userTsid: String): BalanceQueryModel {
        val user = tokenUserQueryService.getUser(userTsid)
        val point = user.point ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        return BalanceQueryModel(user.tsid, point.balance)
    }
}
