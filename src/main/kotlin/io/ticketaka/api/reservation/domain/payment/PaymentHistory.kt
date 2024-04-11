package io.ticketaka.api.reservation.domain.payment

import com.github.f4b6a3.tsid.TsidCreator
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class PaymentHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val tsid: String,
    val transactionKey: String,
    val amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val occurredTime: LocalDateTime,
    val updateTime: LocalDateTime,
    @ManyToOne(targetEntity = Payment::class, optional = false, fetch = FetchType.LAZY)
    val payment: Payment
) {
    enum class Status {
        SUCCESS, CANCEL, ABORT
    }

    companion object {
        fun newInstance(amount: BigDecimal, status: Status, payment: Payment): PaymentHistory {
            val now = LocalDateTime.now()
            return PaymentHistory(
                tsid = TsIdKeyGenerator.next("pmu"),
                transactionKey = TsidCreator.getTsid().toString(),
                amount = amount,
                status = status,
                occurredTime = payment.paymentTime,
                updateTime = now,
                payment = payment
            )
        }
    }
}