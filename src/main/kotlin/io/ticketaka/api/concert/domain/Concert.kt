package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDate

@Entity
@Table(name = "concerts")
class Concert(
    @Id
    val id: Long,
    val date: LocalDate,
) : Persistable<Long> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return isNew
    }

    override fun getId(): Long {
        return id
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    companion object {
        fun newInstance(date: LocalDate): Concert {
            return Concert(
                id = TsIdKeyGenerator.nextLong(),
                date = date,
            )
        }
    }
}
