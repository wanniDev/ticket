package io.ticketaka.api.concert.infrastructure

import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.concert.domain.ConcertSeatUpdater
import io.ticketaka.api.concert.domain.Seat
import io.ticketaka.api.concert.domain.SeatRepository
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class InMemoryCacheConcertSeatUpdater(
    private val caffeineCacheManager: CaffeineCacheManager,
    private val seatRepository: SeatRepository,
) : ConcertSeatUpdater {
    override fun reserve(
        concertId: Long,
        date: LocalDate,
        seatNumbers: List<String>,
    ): Set<Seat> {
        val cache = caffeineCacheManager.getCache("seatNumbers") ?: throw NotFoundException("콘서트별 좌석 캐시가 존재하지 않습니다.")
        synchronized(cache) {
            var seats = cache.get(concertId) { setOf<Seat>() } as Set<Seat>
            if (seats.isEmpty()) {
                seats = seatRepository.findByConcertId(concertId)
            }
            seats.sortedBy { it.number }
                .filter { seatNumbers.contains(it.number) }
                .forEach { it.reserve() }
            cache.put(concertId, seats)
            return seats.filter { seatNumbers.contains(it.number) }.toSet()
        }
    }

    override fun confirm(
        concertId: Long,
        date: String,
        seatNumbers: List<String>,
    ) {
        println("Confirm concert seat")
    }
}
