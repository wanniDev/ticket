package io.ticketaka.api.reservation.domain.payment

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.user.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    val tsid: String,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(
            amount: BigDecimal,
            user: User,
        ): Payment {
            return Payment(
                tsid = TsIdKeyGenerator.next("pm"),
                amount = amount,
                paymentTime = LocalDateTime.now(),
                user = user,
            )
        }
    }
}
