package io.ticketaka.api.reservation.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.reservation.application.dto.BalanceQueryModel
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.application.dto.RechargeCommand
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BalanceService(
    private val userRepository: UserRepository,
    private val paymentService: PaymentService,
    private val pointService: PointService,
) {
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = userRepository.findByTsid(rechargeCommand.userTsid) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
        val userTsid = user.tsid
        val userPointTsid = user.point?.tsid ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        // 실제로는 PG 승인 요청을 수행하는 로직이 들어가야 함
        paymentService.paymentApproval(
            PaymentCommand(
                userTsid = userTsid,
                pointTsid = userPointTsid,
                amount = rechargeCommand.amount,
            ),
        )

        user.rechargePoint(rechargeCommand.amount)

        pointService.recordRechargePointHistory(userTsid, userPointTsid)
    }

    fun getBalance(userTsid: String): BalanceQueryModel {
        val user = userRepository.findByTsid(userTsid) ?: throw NotFoundException("사용자를 찾을 수 없습니다.")
        val point = user.point ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
        return BalanceQueryModel(user.tsid, point.balance)
    }
}
