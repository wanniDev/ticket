package io.ticketaka.api.point.domain

interface PointHistoryRepository {
    fun save(pointHistory: PointHistory): PointHistory

    fun saveAll(pointHistories: List<PointHistory>)
}
