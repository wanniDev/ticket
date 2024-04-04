package io.hhplus.ticket.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
class User(
    @Id
    val id: String,
    val username: String,
    val balance: Long,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
)