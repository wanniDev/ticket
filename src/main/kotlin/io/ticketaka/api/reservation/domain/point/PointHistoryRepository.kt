package io.ticketaka.api.reservation.domain.point

interface PointHistoryRepository {
    fun save(pointHistory: PointHistory): PointHistory
}