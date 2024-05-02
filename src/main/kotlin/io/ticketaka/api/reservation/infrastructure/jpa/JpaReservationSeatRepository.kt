package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.reservation.ReservationSeat
import org.springframework.data.jpa.repository.JpaRepository

interface JpaReservationSeatRepository : JpaRepository<ReservationSeat, Long>
