package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    val tsid: String,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Concert::class, optional = false, fetch = FetchType.LAZY)
    val concert: Concert,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat
) {
    fun confirm() {
        if (status != Status.PENDING) {
            throw IllegalStateException("Reservation is not pending")
        }
        status = Status.CONFIRMED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class Status {
        PENDING, CONFIRMED, CANCELLED
    }

    companion object {
        fun createPendingReservation(user: User, concert: Concert, seat: Seat): Reservation {
            return Reservation(
                TsIdKeyGenerator.next("rev"),
                Status.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5L),
                user,
                concert,
                seat
            )
        }
    }
}