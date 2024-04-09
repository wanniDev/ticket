package io.ticketaka.api.payment.domain

import io.ticketaka.api.point.domain.Point
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class PaymentUsage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val transactionKey: String,
    val amount: BigDecimal,
    val status: String, // TODO pg사의 결제 상태 리서치하고 enum 으로 변경
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point,
    @ManyToOne(targetEntity = Payment::class, optional = false, fetch = FetchType.LAZY)
    val payment: Payment,
)