package io.ticketaka.api.payment.application

import io.ticketaka.api.reservation.application.PaymentService
import io.ticketaka.api.reservation.application.PointService
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.Payment
import io.ticketaka.api.reservation.domain.payment.PaymentRepository
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.application.TokenUserService
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
            User("userTsid1", point)
        val userTsid = user.tsid
        val pointTsid = point.tsid
        val mockPaymentRepository =
            mock<PaymentRepository> {
                on { save(any()) } doReturn Payment.newInstance(1000.toBigDecimal(), user, point)
            }
        val mockPointService =
            mock<PointService> {
                on { getPoint(pointTsid) } doReturn point
            }
        val mockTokenUserService =
            mock<TokenUserService> {
                on { getUser(userTsid) } doReturn user
            }
        val paymentService = PaymentService(mockPaymentRepository, mockPointService, mockTokenUserService)
        val paymentCommand =
            PaymentCommand(
                userTsid = userTsid,
                pointTsid = pointTsid,
                amount = 1000.toBigDecimal(),
            )

        // when
        paymentService.paymentApproval(paymentCommand)

        // then
        verify(mockPaymentRepository, times(1)).save(any())
    }
}
