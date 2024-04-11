package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.Payment
import io.ticketaka.api.reservation.domain.payment.PaymentHistory
import io.ticketaka.api.reservation.domain.payment.PaymentHistoryRepository
import io.ticketaka.api.reservation.domain.payment.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
        val payment = paymentRepository.save(Payment.newInstance(paymentCommand.amount))
        // 예약, 대기열 비즈니스 로직에 집중하기 위해, 결제가 실패하는 경우는 고려하지 않겠습니다.
        paymentHistoryRepository.save(PaymentHistory.newInstance(paymentCommand.amount, PaymentHistory.Status.SUCCESS, payment))
    }
}