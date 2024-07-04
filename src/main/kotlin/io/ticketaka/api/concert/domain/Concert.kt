package io.ticketaka.api.concert.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "concerts")
class Concert(
    @Id
    val id: Long,
    val date: LocalDate,
) : Persistable<Long> {
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    override fun getId(): Long = id

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    companion object {
        fun newInstance(date: LocalDate): Concert =
            Concert(
                id = TsIdKeyGenerator.nextLong(),
                date = date,
            )
    }
}
