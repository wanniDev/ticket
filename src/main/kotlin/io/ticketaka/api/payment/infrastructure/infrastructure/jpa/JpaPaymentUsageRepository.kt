package io.ticketaka.api.payment.infrastructure.infrastructure.jpa

import io.ticketaka.api.payment.domain.PaymentUsage
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentUsageRepository: JpaRepository<PaymentUsage, Long>