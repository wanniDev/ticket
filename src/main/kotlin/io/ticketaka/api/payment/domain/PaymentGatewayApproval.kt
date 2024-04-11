package io.ticketaka.api.payment.domain

interface PaymentGatewayApproval {
    fun approve(paymentInfoData: PaymentInfoData): Boolean
}