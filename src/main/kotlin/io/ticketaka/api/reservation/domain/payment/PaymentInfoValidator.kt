package io.ticketaka.api.reservation.domain.payment

interface PaymentInfoValidator {
    fun validate(paymentInfoData: PaymentInfoData)
}