package io.ticketaka.api.payment.application

import io.ticketaka.api.payment.application.dto.PaymentCommand
import io.ticketaka.api.payment.domain.PaymentGatewayApproval
import io.ticketaka.api.payment.domain.PaymentInfoValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentInfoValidator: PaymentInfoValidator,
    private val paymentGatewayApproval: PaymentGatewayApproval,
) {
    @Transactional
    fun pay(paymentCommand: PaymentCommand) {
        paymentInfoValidator.validate(paymentCommand.toDomain())
        paymentGatewayApproval.approve(paymentCommand.toDomain())
    }
}