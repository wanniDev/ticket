package io.ticketaka.api.payment.infrastructure.infrastructure.persistence

import io.ticketaka.api.payment.domain.PaymentHistory
import io.ticketaka.api.payment.domain.PaymentUsageRepository
import io.ticketaka.api.payment.infrastructure.infrastructure.jpa.JpaPaymentUsageRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentUsageRepositoryComposition(
    private val jpaPaymentUsageRepository: JpaPaymentUsageRepository
): PaymentUsageRepository {
    override fun save(payment: PaymentHistory): PaymentHistory {
        return jpaPaymentUsageRepository.save(payment)
    }
}