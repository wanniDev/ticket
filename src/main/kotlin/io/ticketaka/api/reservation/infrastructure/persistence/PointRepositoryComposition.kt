package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaPointRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

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

    override fun findById(pointId: Long): Point? {
        return jpaPointRepository.findById(pointId).orElse(null)
    }

    override fun updateBalance(
        pointId: Long,
        balance: BigDecimal,
    ) {
        jpaPointRepository.updateBalanceById(pointId, balance)
    }

    override fun findByTsidForUpdate(tsid: String): Point? {
        return jpaPointRepository.findPointByTsid(tsid)
    }

    override fun findByIdForUpdate(pointId: Long): Point? {
        return jpaPointRepository.findPointById(pointId)
    }
}
