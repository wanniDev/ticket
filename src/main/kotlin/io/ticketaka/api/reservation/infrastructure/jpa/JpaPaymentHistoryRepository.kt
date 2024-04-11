package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.payment.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentHistoryRepository: JpaRepository<PaymentHistory, Long>