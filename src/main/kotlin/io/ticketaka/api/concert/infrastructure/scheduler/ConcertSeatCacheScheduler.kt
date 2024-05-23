package io.ticketaka.api.concert.infrastructure.scheduler

import io.ticketaka.api.concert.domain.ConcertRepository
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ConcertSeatCacheScheduler(
    private val seatRepository: SeatRepository,
    private val concertRepository: ConcertRepository,
    private val cacheManager: CaffeineCacheManager,
) {
    @Scheduled(fixedDelay = 1000)
    fun refreshConcertSeatCache() {
        concertRepository.findAll().forEach { concert ->
            val seats = seatRepository.findByConcertId(concert.getId())
            cacheManager.getCache("seatNumbers")?.put(concert.getId(), seats)
        }
    }
}
