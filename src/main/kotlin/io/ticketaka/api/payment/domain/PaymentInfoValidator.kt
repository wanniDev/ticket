package io.ticketaka.api.payment.domain

interface PaymentInfoValidator {
    fun validate(paymentInfoData: PaymentInfoData)
}