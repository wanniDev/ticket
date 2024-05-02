package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.Payment
import io.ticketaka.api.reservation.domain.payment.PaymentRepository
import io.ticketaka.api.user.application.TokenUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val tokenUserService: TokenUserService,
    private val paymentRepository: PaymentRepository,
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        val user = tokenUserService.getUser(paymentCommand.userTsid)
        Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
        paymentRepository.save(Payment.newInstance(paymentCommand.amount, user))
    }
}
