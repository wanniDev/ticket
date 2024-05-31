package io.ticketaka.api.user.domain

import io.ticketaka.api.common.domain.AbstractAggregateRoot
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tokens")
class Token protected constructor(
    var tsid: String,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var status: Status,
    val userId: Long,
) : AbstractAggregateRoot() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

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
                tsid = TsIdKeyGenerator.next("token"),
                issuedTime = LocalDateTime.now(),
                status = Status.ACTIVE,
                userId = userId,
            )
        }

        fun newInstance(
            tsid: String,
            issuedTime: LocalDateTime,
            status: Status,
            userId: Long,
        ): Token {
            return Token(tsid, issuedTime, status, userId)
        }
    }
}
