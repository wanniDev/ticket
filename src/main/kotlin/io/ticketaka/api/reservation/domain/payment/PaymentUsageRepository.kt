package io.ticketaka.api.reservation.domain.payment

interface PaymentUsageRepository {
    fun save(payment: PaymentHistory): PaymentHistory
}