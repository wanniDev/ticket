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
    val status: Status,
    val userId: Long,
) : AbstractAggregateRoot() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class Status {
        ACTIVE,
        EXPIRED,
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
    }
}
