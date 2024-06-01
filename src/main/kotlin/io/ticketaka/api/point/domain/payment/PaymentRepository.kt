package io.ticketaka.api.point.domain.payment

interface PaymentRepository {
    fun save(payment: Payment): Payment

    fun delete(payment: Payment)
}
