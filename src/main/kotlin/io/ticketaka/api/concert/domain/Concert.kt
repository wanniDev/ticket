package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "concerts")
class Concert(
    val tsid: String,
    val date: LocalDate,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun getId(): Long {
        return this.id ?: throw IllegalStateException("Concert Id가 없습니다.")
    }

    companion object {
        fun newInstance(date: LocalDate): Concert {
            return Concert(
                tsid = TsIdKeyGenerator.next("ct"),
                date = date,
            )
        }
    }
}
