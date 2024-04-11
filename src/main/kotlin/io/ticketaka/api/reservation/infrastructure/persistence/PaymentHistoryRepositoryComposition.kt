package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.payment.PaymentHistory
import io.ticketaka.api.reservation.domain.payment.PaymentHistoryRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaPaymentHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentHistoryRepositoryComposition(
    private val paymentHistoryRepository: JpaPaymentHistoryRepository
): PaymentHistoryRepository {
    override fun save(paymentHistory: PaymentHistory): PaymentHistory {
        return paymentHistoryRepository.save(paymentHistory)
    }
}