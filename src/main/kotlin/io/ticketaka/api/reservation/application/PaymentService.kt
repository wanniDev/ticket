package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.Payment
import io.ticketaka.api.reservation.domain.payment.PaymentRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val pointService: PointService,
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
        paymentRepository.save(Payment.newInstance(paymentCommand.amount, paymentCommand.userId, paymentCommand.pointId))
    }

    @Async
    @Transactional(propagation = Propagation.NESTED)
    fun paymentApprovalAsync(
        paymentCommand: PaymentCommand,
        rollbackBalance: BigDecimal,
    ) {
        val payment =
            paymentRepository.save(
                Payment.newInstance(
                    paymentCommand.amount,
                    paymentCommand.userId,
                    paymentCommand.pointId,
                ),
            )
        try {
            Thread.sleep((500..1000).random().toLong()) // PG 승인 요청 시간 대기
            throw RuntimeException("PG 승인 실패")
        } catch (e: Exception) {
            paymentRepository.delete(payment) // PG 승인 실패 시 결제 정보 삭제, 실제로는 PG 취소 요청을 수행, 혹은 tid 로 결제 데이터 조회 및 삭제를 해야함
            pointService.rollbackPoint(paymentCommand.userId, paymentCommand.pointId, rollbackBalance)
            e.printStackTrace()
        }
    }
}
