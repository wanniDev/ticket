package io.ticketaka.api.reservation.application

import io.ticketaka.api.reservation.application.dto.PaymentCommand
import io.ticketaka.api.reservation.domain.payment.PaymentGatewayApproval
import io.ticketaka.api.reservation.domain.payment.PaymentInfoValidator
import io.ticketaka.api.reservation.domain.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PaymentService(
    private val paymentInfoValidator: PaymentInfoValidator,
    private val paymentGatewayApproval: PaymentGatewayApproval,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun paymentApproval(paymentCommand: PaymentCommand) {
        paymentInfoValidator.validate(paymentCommand.toDomain())
        paymentGatewayApproval.approve(paymentCommand.toDomain())
    }

    @Transactional
    fun payAndConfirm(paymentCommand: PaymentCommand) {
        paymentApproval(paymentCommand)

    }
}