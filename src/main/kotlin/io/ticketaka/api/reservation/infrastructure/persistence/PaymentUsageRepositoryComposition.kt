package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.payment.PaymentHistory
import io.ticketaka.api.reservation.domain.payment.PaymentUsageRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaPaymentUsageRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentUsageRepositoryComposition(
    private val jpaPaymentUsageRepository: JpaPaymentUsageRepository
): PaymentUsageRepository {
    override fun save(payment: PaymentHistory): PaymentHistory {
        return jpaPaymentUsageRepository.save(payment)
    }
}