package io.ticketaka.api.point.domain

import io.ticketaka.api.common.infrastructure.IdempotentKeyGenerator
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
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "idempotent")
class Idempotent(
    @Id
    var id: Long,
    val key: String,
) : Persistable<Long> {
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = null
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

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
        fun newInstance(
            userId: Long,
            tokenId: Long,
            amount: BigDecimal,
            transactionType: PointHistory.TransactionType,
        ): Idempotent {
            return Idempotent(TsIdKeyGenerator.nextLong(), IdempotentKeyGenerator.generate(userId, tokenId, amount, transactionType.name))
        }
    }
}
