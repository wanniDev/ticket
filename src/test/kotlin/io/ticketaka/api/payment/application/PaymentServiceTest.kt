package io.ticketaka.api.payment.application

import io.ticketaka.api.point.application.PaymentService
import io.ticketaka.api.point.application.dto.PaymentCommand
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.payment.Payment
import io.ticketaka.api.point.domain.payment.PaymentRepository
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PaymentServiceTest {
    @Test
    fun `when payment approval api invoked verify payment information before approval`() {
        // given
        val point = Point.newInstance()
        val user =
            User.newInstance(point.id)
        val userId = user.id
        val pointId = point.id
        val mockPaymentRepository =
            mock<PaymentRepository> {
                on { save(any()) } doReturn Payment.newInstance(1000.toBigDecimal(), userId, pointId)
            }

        val paymentService = PaymentService(mockPaymentRepository, mock(), mock())
        val paymentCommand =
            PaymentCommand(
                userId = userId,
                pointId = pointId,
                amount = 1000.toBigDecimal(),
            )

        // when
        paymentService.paymentApproval(paymentCommand)

        // then
        verify(mockPaymentRepository, times(1)).save(any())
    }
}
