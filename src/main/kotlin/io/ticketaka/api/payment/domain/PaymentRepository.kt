package io.ticketaka.api.payment.domain

interface PaymentRepository {
    fun save(payment: Payment): Payment
}