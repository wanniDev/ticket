package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class ConcertDate(
    val tsid: String,
    val date: LocalDate
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(date: LocalDate): ConcertDate {
            return ConcertDate(
                tsid = TsIdKeyGenerator.next("cd"),
                date = date
            )
        }
    }
}