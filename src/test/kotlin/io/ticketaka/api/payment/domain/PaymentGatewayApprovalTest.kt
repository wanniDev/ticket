package io.ticketaka.api.payment.domain

import io.ticketaka.api.payment.infrastructure.pg.dummy.DummyPaymentGatewayApproval
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.reservation.domain.payment.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PaymentGatewayApprovalTest {
    @Test
    fun `payment approval api invoked verify payment information before approval`() {
        // given
        val point = Point.newInstance()
        val mockPaymentRepository = mock<PaymentRepository>() {
            on { save(any()) } doReturn Payment.newInstance(20000.toBigDecimal())
        }
        val mockPaymentHistoryRepository = mock<PaymentUsageRepository>() {
            on { save(any()) } doReturn PaymentHistory.newInstance(
                "transactionKey",
                20000.toBigDecimal(),
                "cardExpiration",
                point,
                Payment.newInstance(20000.toBigDecimal())
            )
        }
        val mockPointRepository = mock<PointRepository>() {
            on { findByTsid(any()) } doReturn point
        }
        val paymentGatewayApproval = DummyPaymentGatewayApproval(
            mockPaymentRepository,
            mockPaymentHistoryRepository,
            mockPointRepository
        )
        // when
        paymentGatewayApproval.approve(
            PaymentInfoData(
                "userTsid",
                20000.toBigDecimal(),
                "oderId",
                "orderName",
                "1234-1234-1234-1234",
                26,
                11,
                "12**",
                "991234-1******"
            )
        )

        // then
        verify(mockPointRepository).findByTsid(any())
        verify(mockPaymentRepository).save(any())
        verify(mockPaymentHistoryRepository).save(any())
    }
}