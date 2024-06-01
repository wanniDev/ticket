package io.ticketaka.api.user.domain

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

@Entity
@Table(name = "tokens")
class Token protected constructor(
    @Id
    val id: Long,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val userId: Long,
) : AbstractAggregateRoot(), Persistable<Long> {
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

    enum class Status {
        ACTIVE,
        DEACTIVATED,
        EXPIRED,
    }

    fun activate() {
        if (status == Status.ACTIVE) {
            return
        }
        status = Status.ACTIVE
    }

    fun deactivate() {
        if (status == Status.DEACTIVATED) {
            return
        }
        status = Status.DEACTIVATED
    }

    fun expired() {
        if (status == Status.EXPIRED) {
            return
        }
        status = Status.EXPIRED
    }

    fun isExpired(): Boolean {
        return status == Status.EXPIRED || issuedTime.plusMinutes(5).isBefore(LocalDateTime.now())
    }

    fun isDeactivated(): Boolean {
        return status == Status.DEACTIVATED
    }

    init {
        registerEvent(TokenCreatedEvent(this))
    }

    companion object {
        fun newInstance(userId: Long): Token {
            return Token(
                id = TsIdKeyGenerator.nextLong(),
                issuedTime = LocalDateTime.now(),
                status = Status.ACTIVE,
                userId = userId,
            )
        }

        fun newInstance(
            id: Long,
            issuedTime: LocalDateTime,
            status: Status,
            userId: Long,
        ): Token {
            return Token(id, issuedTime, status, userId)
        }
    }
}
