package io.ticketaka.api.reservation.infrastructure.jpa

import io.ticketaka.api.point.domain.PointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPointHistoryRepository : JpaRepository<PointHistory, Long>
