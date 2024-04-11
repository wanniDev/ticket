package io.ticketaka.api.reservation.domain.payment

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Payment(
    val tsid: String,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun newInstance(amount: BigDecimal): Payment {
            return Payment(
                tsid = TsIdKeyGenerator.next("pm"),
                amount = amount,
                paymentTime = LocalDateTime.now(),
            )
        }
    }
}