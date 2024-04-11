package io.ticketaka.api.payment.infrastructure.infrastructure.jpa

import io.ticketaka.api.payment.domain.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentRepository: JpaRepository<Payment, Long>