package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.reservation.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface JpaReservationRepository : JpaRepository<Reservation, Long>
