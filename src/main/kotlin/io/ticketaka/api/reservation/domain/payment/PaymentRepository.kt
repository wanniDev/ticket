package io.ticketaka.api.reservation.domain.payment

interface PaymentRepository {
    fun save(payment: Payment): Payment

    fun delete(payment: Payment)
}
