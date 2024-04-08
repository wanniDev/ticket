package io.hhplus.ticket.reservation.domain

import io.hhplus.ticket.concert.domain.Seat
import io.hhplus.ticket.balance.domain.Balance
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    @ManyToOne(targetEntity = Balance::class, optional = false, fetch = FetchType.LAZY)
    val balance: Balance,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat
) {
    enum class Status {
        PENDING, CONFIRMED, CANCELLED
    }
}