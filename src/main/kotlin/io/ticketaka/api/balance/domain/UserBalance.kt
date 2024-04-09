package io.ticketaka.api.balance.domain

import io.ticketaka.api.user.domain.User
import jakarta.persistence.*

@Entity
class UserBalance(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Balance::class, optional = false, fetch = FetchType.LAZY)
    val balance: Balance
)