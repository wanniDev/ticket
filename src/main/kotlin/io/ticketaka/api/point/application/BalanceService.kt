package io.ticketaka.api.point.application

import io.ticketaka.api.payment.application.PaymentService
import io.ticketaka.api.payment.application.dto.PaymentCommand
import io.ticketaka.api.point.application.dto.BalanceQueryModel
import io.ticketaka.api.point.application.dto.RechargeCommand
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BalanceService(
    private val userRepository: UserRepository,
    private val paymentService: PaymentService
) {
    @Transactional
    fun recharge(rechargeCommand: RechargeCommand) {
        val user = userRepository.findByTsid(rechargeCommand.userTsid)

        // 실제로는 PG 승인 요청을 수행하는 로직이 들어가야 함
        paymentService.paymentApproval(
            PaymentCommand(
                userTsid = user.tsid,
                amount = rechargeCommand.amount,
                orderId = rechargeCommand.orderId,
                orderName = rechargeCommand.orderName,
                cardNumber = rechargeCommand.cardNumber,
                cardExpirationYear = rechargeCommand.cardExpirationYear,
                cardExpirationMonth = rechargeCommand.cardExpirationMonth,
                cardPasswordPrefix = rechargeCommand.cardPasswordPrefix,
                customerIdentityNumber = rechargeCommand.customerIdentityNumber
            )
        )

        user.rechargePoint(rechargeCommand.amount)
    }

    fun getBalance(userTsid: String): BalanceQueryModel {
        val user = userRepository.findByTsid(userTsid)
        val point = user.point
        return BalanceQueryModel(user.tsid, point.balance)
    }
}