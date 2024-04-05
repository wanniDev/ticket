package io.hhplus.ticket.seat.domain

import io.hhplus.ticket.concertdate.domain.ConcertDate
import jakarta.persistence.*

@Entity
class Seat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val number: Int,
    @Enumerated(EnumType.STRING)
    val status: Status,
    @ManyToOne(targetEntity = ConcertDate::class, optional = false, fetch = FetchType.LAZY)
    val concertDate: ConcertDate
) {
    enum class Status {
        AVAILABLE, RESERVED, OCCUPIED
    }
}