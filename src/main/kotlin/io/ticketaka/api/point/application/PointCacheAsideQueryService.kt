package io.ticketaka.api.point.application

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.point.domain.PointRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PointCacheAsideQueryService(
    private val pointRepository: PointRepository,
) {
    @Cacheable(value = ["point"], key = "#pointId")
    fun getPoint(pointId: Long): Point {
        return pointRepository.findById(pointId) ?: throw NotFoundException("포인트를 찾을 수 없습니다.")
    }
}
