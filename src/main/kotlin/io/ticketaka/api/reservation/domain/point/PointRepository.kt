package io.ticketaka.api.reservation.domain.point

import java.math.BigDecimal

interface PointRepository {
    fun save(point: Point): Point

    fun findByTsid(tsid: String): Point?

    fun findById(pointId: Long): Point?

    fun updateBalance(
        pointId: Long,
        balance: BigDecimal,
    )

    fun findByTsidForUpdate(tsid: String): Point?
}
