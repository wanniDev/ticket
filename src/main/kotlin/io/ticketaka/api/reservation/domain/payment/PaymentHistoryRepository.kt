package io.ticketaka.api.reservation.domain.payment

interface PaymentHistoryRepository {
    fun save(paymentHistory: PaymentHistory): PaymentHistory
}