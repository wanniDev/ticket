package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.concert.domain.Seat
import jakarta.persistence.*

@Entity
class ReservationSeat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat,
    @ManyToOne(targetEntity = Reservation::class, optional = false, fetch = FetchType.LAZY)
    val reservation: Reservation
) {
    companion object {
        fun create(seat: Seat, reservation: Reservation): ReservationSeat {
            return ReservationSeat(
                seat = seat,
                reservation = reservation
            )
        }
    }
}