package io.ticketaka.api.point.application

import io.ticketaka.api.point.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.Payment
import io.ticketaka.api.reservation.domain.payment.PaymentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val pointService: PointService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
        paymentRepository.save(Payment.newInstance(paymentCommand.amount, paymentCommand.userId, paymentCommand.pointId))
    }

    @Async
    @Transactional(propagation = Propagation.NESTED)
    fun paymentApprovalAsync(paymentCommand: PaymentCommand) {
        try {
            Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
            val payment = Payment.newInstance(paymentCommand.amount, paymentCommand.userId, paymentCommand.pointId)
            paymentRepository.save(payment)
            payment.pollAllEvents().forEach { applicationEventPublisher.publishEvent(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
