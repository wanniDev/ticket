package io.ticketaka.api.point.domain

import java.math.BigDecimal

interface PointRepository {
    fun save(point: Point): Point

    fun findById(pointId: Long): Point?

    fun updateBalance(
        pointId: Long,
        balance: BigDecimal,
    )

    fun findByIdForUpdate(pointId: Long): Point?
}
