package io.hhplus.ticket.token.domain

import io.hhplus.ticket.balance.domain.Balance
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Token(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    @ManyToOne(targetEntity = Balance::class, optional = false, fetch = FetchType.LAZY)
    val balance: Balance,
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