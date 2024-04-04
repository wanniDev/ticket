package io.hhplus.ticket.reservation.domain

import io.hhplus.ticket.seat.domain.Seat
import io.hhplus.ticket.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    @Id
    val id: String,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat
) {
    enum class Status {
        PENDING, CONFIRMED, CANCELLED
    }
}