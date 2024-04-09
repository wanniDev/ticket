package io.ticketaka.api.token.domain

import io.ticketaka.api.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Token(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val userTsid: String
) {
    enum class Status {
        ACTIVE, EXPIRED
    }
}