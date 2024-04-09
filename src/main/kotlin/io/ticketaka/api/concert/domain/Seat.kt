package io.ticketaka.api.concert.domain

import jakarta.persistence.*

@Entity
class Seat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val number: String,
    @Enumerated(EnumType.STRING)
    val status: Status,
    @ManyToOne(targetEntity = ConcertDate::class, optional = false, fetch = FetchType.LAZY)
    val concertDate: ConcertDate
) {
    enum class Status {
        AVAILABLE, RESERVED, OCCUPIED
    }
}