package io.ticketaka.api.point.application

import io.ticketaka.api.point.application.dto.PaymentCommand
import io.ticketaka.api.point.domain.payment.Payment
import io.ticketaka.api.point.domain.payment.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
        paymentRepository.save(Payment.newInstance(paymentCommand.amount, paymentCommand.userId, paymentCommand.pointId))
    }
}
