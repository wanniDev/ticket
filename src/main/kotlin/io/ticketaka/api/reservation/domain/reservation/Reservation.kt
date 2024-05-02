package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.concert.domain.Concert
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.user.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
class Reservation(
    @Id
    val tsid: String,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Concert::class, optional = false, fetch = FetchType.LAZY)
    val concert: Concert,
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    var seats: Set<ReservationSeat> = emptySet(),
) {
    fun confirm() {
        if (status != Status.PENDING) {
            throw IllegalStateException("Reservation is not pending")
        }
        status = Status.CONFIRMED
    }

    fun allocate(seats: Set<Seat>) {
        if (status != Status.PENDING) {
            throw IllegalStateException("Reservation is not pending")
        }
        this.seats = seats.map { ReservationSeat.create(it, this) }.toSet()
    }

    var id: Long? = null

    enum class Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
    }

    companion object {
        fun createPendingReservation(
            user: User,
            concert: Concert,
        ): Reservation {
            return Reservation(
                TsIdKeyGenerator.next("rev"),
                Status.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5L),
                user,
                concert,
            )
        }
    }
}
