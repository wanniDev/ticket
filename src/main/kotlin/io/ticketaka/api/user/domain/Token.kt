package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
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