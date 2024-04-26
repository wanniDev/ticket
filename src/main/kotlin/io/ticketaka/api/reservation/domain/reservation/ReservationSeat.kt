package io.ticketaka.api.reservation.domain.reservation

import io.ticketaka.api.concert.domain.Seat
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "reservations_seats")
class ReservationSeat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(targetEntity = Seat::class, optional = false, fetch = FetchType.LAZY)
    val seat: Seat,
    @ManyToOne(targetEntity = Reservation::class, optional = false, fetch = FetchType.LAZY)
    val reservation: Reservation,
) {
    companion object {
        fun create(
            seat: Seat,
            reservation: Reservation,
        ): ReservationSeat {
            return ReservationSeat(
                seat = seat,
                reservation = reservation,
            )
        }
    }
}
