package io.ticketaka.api.point.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Point(
    val tsid: String,
    val balance: BigDecimal,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}