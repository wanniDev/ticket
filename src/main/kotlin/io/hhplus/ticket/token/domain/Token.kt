package io.hhplus.ticket.token.domain

import io.hhplus.ticket.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Token(
    @Id
    val id: String,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    val expirationTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
) {
    enum class Status {
        ACTIVE, INACTIVE
    }
}