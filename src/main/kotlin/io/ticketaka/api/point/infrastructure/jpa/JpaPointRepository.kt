package io.ticketaka.api.point.infrastructure.jpa

import io.ticketaka.api.point.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPointRepository: JpaRepository<Point, Long> {
    fun findByTsid(tsid: String): Point?
}