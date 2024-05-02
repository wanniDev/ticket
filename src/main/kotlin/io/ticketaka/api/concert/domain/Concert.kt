package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "concerts")
class Concert(
    @Id
    val tsid: String,
    val date: LocalDate,
) {
    var id: Long? = null

    companion object {
        fun newInstance(date: LocalDate): Concert {
            return Concert(
                tsid = TsIdKeyGenerator.next("cd"),
                date = date,
            )
        }
    }
}
