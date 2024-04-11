package io.ticketaka.api.payment.application

import io.ticketaka.api.reservation.domain.payment.PaymentGatewayApproval
import io.ticketaka.api.reservation.domain.payment.PaymentInfoValidator
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.reservation.application.PaymentService
import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.user.domain.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class PaymentServiceTest {
    @Test
    fun `when payment approval api invoked verify payment information before approval`() {
        // given
        val point = Point.newInstance()
        val user =
            User("userTsid1", point)
        val paymentInfoValidator = mock<PaymentInfoValidator>()
        val paymentGatewayApproval = mock<PaymentGatewayApproval>()
        val paymentService = PaymentService(
            paymentInfoValidator,
            paymentGatewayApproval
        )
        val paymentCommand = PaymentCommand(
            user.tsid,
            20000.toBigDecimal(),
            "ord-123456789",
            "cardExpiration",
            "cardCvc",
            2024,
            4,
            "12**",
            "991299-1******",
        )
        val paymentInfoData = paymentCommand.toDomain()

        // when
        paymentService.paymentApproval(paymentCommand)

        // then
        verify(paymentInfoValidator).validate(paymentInfoData)
        verify(paymentGatewayApproval).approve(paymentInfoData)
    }
}