package io.ticketaka.api.payment.domain

interface PaymentUsageRepository {
    fun save(payment: PaymentUsage): PaymentUsage
}