package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tokens")
class Token protected constructor(
    var tsid: String? = null,
    val issuedTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
                user = user,
            )
        }
    }
}
