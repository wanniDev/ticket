package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.payment.PaymentHistory
import io.ticketaka.api.reservation.domain.payment.PaymentHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentHistoryRepositoryComposition(
    private val paymentHistoryRepository: PaymentHistoryRepository
): PaymentHistoryRepository {
    override fun save(paymentHistory: PaymentHistory): PaymentHistory {
        return paymentHistoryRepository.save(paymentHistory)
    }
}