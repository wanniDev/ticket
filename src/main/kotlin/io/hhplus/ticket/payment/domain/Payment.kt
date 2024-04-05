package io.hhplus.ticket.payment.domain

import io.hhplus.ticket.reservation.domain.Reservation
import io.hhplus.ticket.user.domain.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Payment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Reservation::class, optional = false, fetch = FetchType.LAZY)
    val reservation: Reservation
)