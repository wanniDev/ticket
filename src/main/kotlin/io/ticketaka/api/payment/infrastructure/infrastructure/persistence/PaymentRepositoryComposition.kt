package io.ticketaka.api.payment.infrastructure.infrastructure.persistence

import io.ticketaka.api.payment.domain.Payment
import io.ticketaka.api.payment.domain.PaymentRepository
import io.ticketaka.api.payment.infrastructure.infrastructure.jpa.JpaPaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryComposition(
    private val jpaPaymentRepository: JpaPaymentRepository
): PaymentRepository {
    override fun save(payment: Payment): Payment {
        return jpaPaymentRepository.save(payment)
    }
}