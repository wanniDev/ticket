package io.ticketaka.api.point.infrastructure.persistence

import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.point.infrastructure.jpa.JpaPointRepository
import org.springframework.stereotype.Repository

@Repository
class PointRepositoryComposition(
    private val jpaPointRepository: JpaPointRepository
): PointRepository {
    override fun save(point: Point): Point {
        return jpaPointRepository.save(point)
    }

    override fun findByTsid(tsid: String): Point? {
        return jpaPointRepository.findByTsid(tsid) ?: throw RuntimeException("Point not found")
    }
}