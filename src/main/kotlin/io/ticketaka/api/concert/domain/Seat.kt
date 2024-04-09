package io.ticketaka.api.concert.domain

import jakarta.persistence.*

@Entity
class Seat(
    val tsid: String,
    val number: String,
    @Enumerated(EnumType.STRING)
    val status: Status,
    @ManyToOne(targetEntity = ConcertDate::class, optional = false, fetch = FetchType.LAZY)
    val concertDate: ConcertDate
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class Status {
        AVAILABLE, RESERVED, OCCUPIED
    }
}