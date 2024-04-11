package io.ticketaka.api.payment.domain

interface PaymentUsageRepository {
    fun save(payment: PaymentHistory): PaymentHistory
}