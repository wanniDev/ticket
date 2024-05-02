package io.ticketaka.api.reservation.domain.payment

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    @Id
    val tsid: String,
    val amount: BigDecimal,
    val paymentTime: LocalDateTime,
    val userTsid: String,
    val pointTsid: String,
) {
    var id: Long? = null

    companion object {
        fun newInstance(
            amount: BigDecimal,
            userTsid: String,
            pointTsid: String,
        ): Payment {
            return Payment(
                tsid = TsIdKeyGenerator.next("pm"),
                amount = amount,
                userTsid = userTsid,
                paymentTime = LocalDateTime.now(),
                pointTsid = pointTsid,
            )
        }
    }
}
