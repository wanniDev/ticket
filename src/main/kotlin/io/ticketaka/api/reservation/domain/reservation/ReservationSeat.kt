package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable

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

    companion object {
        fun create(
            seatId: Long,
            reservationId: Long,
        ): ReservationSeat {
            return ReservationSeat(
                id = TsIdKeyGenerator.nextLong(),
                seatId = seatId,
                reservationId = reservationId,
            )
        }
    }
}
