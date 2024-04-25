package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.reservation.domain.point.PointRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaPointRepository
import org.springframework.stereotype.Repository

@Repository
class PointRepositoryComposition(
    private val jpaPointRepository: JpaPointRepository,
) : PointRepository {
    override fun save(point: Point): Point {
        return jpaPointRepository.save(point)
    }

    override fun findByTsid(tsid: String): Point? {
        return jpaPointRepository.findByTsid(tsid)
    }
}
