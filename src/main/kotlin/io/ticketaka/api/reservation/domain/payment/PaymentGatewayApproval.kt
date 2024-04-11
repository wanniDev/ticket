package io.ticketaka.api.reservation.domain.payment

interface PaymentGatewayApproval {
    fun approve(paymentInfoData: PaymentInfoData)
}