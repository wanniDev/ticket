package io.hhplus.ticket.balance.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Balance(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val balance: Long,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
)