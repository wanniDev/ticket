package io.ticketaka.api.payment.infrastructure.pg.dummy

import io.ticketaka.api.payment.domain.*
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Component
class DummyPaymentGatewayApproval(
    private val paymentRepository: PaymentRepository,
    private val paymentUsageRepository: PaymentUsageRepository,
    private val pointRepository: PointRepository
): PaymentGatewayApproval {
    override fun approve(paymentInfoData: PaymentInfoData) {
        val requestedTime = LocalDateTime.now()

        Thread.sleep(Random.nextLong(500, 1000)) // 실제로

        val paymentResult = PaymentResultData( // 실제로는 PG 승인 요청 결과로 받아옴
            getDummyTransactionKey(),
            paymentInfoData.amount,
            requestedTime,
            LocalDateTime.now(),
            "APPROVED"
        )
        // 실제 PG 승인 요청을 할 경우, 그리고 그 요청이 실패할 경우, 예외를 throw 하여 아래, db 저장 로직이 실행되지 않도록 해야 함

        val point = pointRepository.findByTsid(paymentInfoData.userTsid)!!
        val payment = paymentRepository.save(Payment.newInstance(paymentInfoData.amount))

        val paymentUsage = PaymentUsage.newInstance(paymentResult.transactionKey, paymentResult.amount, paymentResult.status, point, payment)
        paymentUsageRepository.save(paymentUsage)
    }

    private fun getDummyTransactionKey(): String {
        return "dummyTrans-${LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))}-${Random.nextInt(1000, 9999)}"
    }
}