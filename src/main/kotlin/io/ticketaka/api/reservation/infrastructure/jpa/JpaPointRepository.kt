package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.point.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal

interface JpaPointRepository : JpaRepository<Point, Long> {
    fun findByTsid(tsid: String): Point?

    @Modifying
    @Query("UPDATE Point p SET p.balance = :balance WHERE p.id = :pointId")
    fun updateBalanceById(
        pointId: Long,
        balance: BigDecimal,
    )
}
