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
    val id: Long,
    val date: LocalDate,
) {
    companion object {
        fun newInstance(date: LocalDate): Concert {
            return Concert(
                id = TsIdKeyGenerator.nextLong(),
                date = date,
            )
        }
    }
}
