package io.ticketaka.api.payment.infrastructure.infrastructure.persistence

import io.ticketaka.api.payment.domain.PaymentUsage
import io.ticketaka.api.payment.domain.PaymentUsageRepository
import io.ticketaka.api.payment.infrastructure.infrastructure.jpa.JpaPaymentUsageRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentUsageRepositoryComposition(
    private val jpaPaymentUsageRepository: JpaPaymentUsageRepository
): PaymentUsageRepository {
    override fun save(payment: PaymentUsage): PaymentUsage {
        return jpaPaymentUsageRepository.save(payment)
    }
}