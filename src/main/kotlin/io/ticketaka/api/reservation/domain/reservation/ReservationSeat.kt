package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

@Entity
@Table(name = "reservations_seats")
class ReservationSeat(
    @Id
    val id: Long,
    val seatId: Long,
    val reservationId: Long,
) : Persistable<Long> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    override fun getId(): Long = id

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    companion object {
        fun create(
            seatId: Long,
            reservationId: Long,
        ): ReservationSeat =
            ReservationSeat(
                id = TsIdKeyGenerator.nextLong(),
                seatId = seatId,
                reservationId = reservationId,
            )
    }
}
