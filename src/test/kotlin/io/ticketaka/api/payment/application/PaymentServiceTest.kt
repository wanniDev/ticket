package io.ticketaka.api.payment.application

import io.ticketaka.api.payment.application.dto.PaymentCommand
import io.ticketaka.api.payment.domain.PaymentGatewayApproval
import io.ticketaka.api.payment.domain.PaymentInfoValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class PaymentServiceTest {
    @Test
    fun `when payment approval api invoked verify payment information before approval`() {
        // given
        val paymentInfoValidator = mock<PaymentInfoValidator>()
        val paymentGatewayApproval = mock<PaymentGatewayApproval>()
        val paymentService = PaymentService(paymentInfoValidator, paymentGatewayApproval)
        val paymentCommand = PaymentCommand(
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
        paymentService.pay(paymentCommand)

        // then
        verify(paymentInfoValidator).validate(paymentInfoData)
        verify(paymentGatewayApproval).approve(paymentInfoData)
    }
}