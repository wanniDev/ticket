package io.ticketaka.api.point.infrastructure.jpa

import io.ticketaka.api.point.domain.Point
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findPointByTsid(tsid: String): Point?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findPointById(pointId: Long): Point?
}
