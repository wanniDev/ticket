package io.hhplus.ticket.user.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    val username: String,
    val balance: Long,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
)