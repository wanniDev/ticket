package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tokens")
class Token protected constructor(
    @Id
    var tsid: String? = null,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val userTsid: String,
) {
    var id: Long? = null

    enum class Status {
        ACTIVE,
        EXPIRED,
    }

    companion object {
        fun newInstance(user: User): Token {
            return Token(
                tsid = TsIdKeyGenerator.next("token"),
                issuedTime = LocalDateTime.now(),
                status = Status.ACTIVE,
                userTsid = user.tsid,
            )
        }
    }
}
