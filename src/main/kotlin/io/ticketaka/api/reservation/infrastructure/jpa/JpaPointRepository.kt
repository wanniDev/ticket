package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.point.Point
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPointRepository: JpaRepository<Point, Long> {
    fun findByTsid(tsid: String): Point?
}