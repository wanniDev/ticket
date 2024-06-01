package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "reservations_seats")
class ReservationSeat(
    @Id
    val id: Long,
    val seatId: Long,
    val reservationId: Long,
) {
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
