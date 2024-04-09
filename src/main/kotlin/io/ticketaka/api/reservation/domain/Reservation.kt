package io.ticketaka.api.reservation.domain

import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    val tsid: String,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class Status {
        PENDING, CONFIRMED, CANCELLED
    }
}