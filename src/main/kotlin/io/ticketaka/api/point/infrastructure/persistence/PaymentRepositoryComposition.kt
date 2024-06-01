package io.ticketaka.api.point.infrastructure.persistence

import io.ticketaka.api.point.domain.payment.Payment
import io.ticketaka.api.point.domain.payment.PaymentRepository
import io.ticketaka.api.point.infrastructure.jpa.JpaPaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryComposition(
    private val jpaPaymentRepository: JpaPaymentRepository,
) : PaymentRepository {
    override fun save(payment: Payment): Payment {
        return jpaPaymentRepository.save(payment)
    }

    override fun delete(payment: Payment) {
        jpaPaymentRepository.delete(payment)
    }
}
