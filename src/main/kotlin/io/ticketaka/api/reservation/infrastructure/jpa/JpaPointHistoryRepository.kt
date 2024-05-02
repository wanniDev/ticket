package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.reservation.domain.point.PointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPointHistoryRepository : JpaRepository<PointHistory, Long>
