package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.user.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
class Reservation(
    @Id
    val id: Long,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val reservationTime: LocalDateTime,
    val expirationTime: LocalDateTime,
    val userId: Long,
    val concertId: Long,
) : Persistable<Long> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return isNew
    }

    override fun getId(): Long {
        return id
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    fun confirm() {
        validatePending()
        status = Status.CONFIRMED
    }

    fun allocate(seats: Set<Seat>) {
        validatePending()
        seats.map { ReservationSeat.create(it.id, this.id) }.toSet()
    }

    fun validateUser(user: User) {
        if (this.userId != user.id) {
            throw IllegalStateException("User is not matched")
        }
    }

    fun validatePending() {
        if (this.status != Status.PENDING) {
            throw IllegalStateException("Reservation is not pending")
        }
    }

    enum class Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
    }

    companion object {
        fun createPendingReservation(
            userId: Long,
            concertId: Long,
        ): Reservation {
            return Reservation(
                TsIdKeyGenerator.nextLong(),
                Status.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5L),
                userId,
                concertId,
            )
        }
    }
}
