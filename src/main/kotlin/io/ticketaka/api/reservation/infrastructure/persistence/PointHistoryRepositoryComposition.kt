package io.ticketaka.api.reservation.infrastructure.persistence

import io.ticketaka.api.reservation.domain.point.PointHistory
import io.ticketaka.api.reservation.domain.point.PointHistoryRepository
import io.ticketaka.api.reservation.infrastructure.jpa.JpaPointHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepositoryComposition(
    private val jpaPointHistoryRepository: JpaPointHistoryRepository,
) : PointHistoryRepository {
    override fun save(pointHistory: PointHistory): PointHistory {
        return jpaPointHistoryRepository.save(pointHistory)
    }

    override fun saveAll(pointHistories: List<PointHistory>) {
        jpaPointHistoryRepository.saveAll(pointHistories)
    }
}
