package io.ticketaka.api.payment.domain

import io.ticketaka.api.reservation.domain.Reservation
import io.ticketaka.api.point.domain.Point
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
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point,
    @ManyToOne(targetEntity = Reservation::class, optional = false, fetch = FetchType.LAZY)
    val reservation: Reservation
)