package io.ticketaka.api.point.infrastructure.persistence

import io.ticketaka.api.point.domain.PointHistory
import io.ticketaka.api.point.domain.PointHistoryRepository
import io.ticketaka.api.point.infrastructure.jpa.JpaPointHistoryRepository
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
