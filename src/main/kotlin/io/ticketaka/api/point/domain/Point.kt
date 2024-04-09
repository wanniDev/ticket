package io.ticketaka.api.point.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Point(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val balance: BigDecimal,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
)