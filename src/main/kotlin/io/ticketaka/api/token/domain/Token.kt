package io.ticketaka.api.token.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Token protected constructor(
    var tsid: String? = null,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val userTsid: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class Status {
        ACTIVE, EXPIRED
    }

    companion object {
        fun newInstance(user: User): Token {
            return Token(
                tsid = TsIdKeyGenerator.next("token"),
                issuedTime = LocalDateTime.now(),
                status = Status.ACTIVE,
                userTsid = user.tsid
            )
        }
    }
}