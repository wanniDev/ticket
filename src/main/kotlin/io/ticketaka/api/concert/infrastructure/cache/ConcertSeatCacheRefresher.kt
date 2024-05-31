package io.ticketaka.api.concert.infrastructure.cache

import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.stereotype.Component

@Component
class ConcertSeatCacheRefresher(
    private val seatRepository: SeatRepository,
    private val concertRepository: ConcertRepository,
    private val cacheManager: CaffeineCacheManager,
) {
    fun refresh() {
        concertRepository.findAll().forEach { concert ->
            val seats = seatRepository.findByConcertId(concert.getId())
            cacheManager.getCache("seatNumbers")?.put(concert.getId(), seats)
        }
    }

    fun refresh(concertId: Long) {
        val seats = seatRepository.findByConcertId(concertId)
        cacheManager.getCache("seatNumbers")?.put(concertId, seats)
    }
}
