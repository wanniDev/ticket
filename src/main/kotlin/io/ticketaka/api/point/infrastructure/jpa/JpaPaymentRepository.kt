package io.ticketaka.api.point.infrastructure.jpa

import io.ticketaka.api.point.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPaymentRepository : JpaRepository<Payment, Long>
