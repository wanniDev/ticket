package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDate

@Entity
class Concert(
    val tsid: String,
    val price: BigDecimal,
    val date: LocalDate
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(price: BigDecimal, date: LocalDate): Concert {
            return Concert(
                tsid = TsIdKeyGenerator.next("cd"),
                price = price,
                date = date
            )
        }
    }
}