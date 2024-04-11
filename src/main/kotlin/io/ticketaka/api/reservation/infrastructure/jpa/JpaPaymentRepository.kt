package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentRepository: JpaRepository<Payment, Long>