package io.ticketaka.api.point.infrastructure.jpa

import io.ticketaka.api.point.domain.PointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPointHistoryRepository : JpaRepository<PointHistory, Long>
