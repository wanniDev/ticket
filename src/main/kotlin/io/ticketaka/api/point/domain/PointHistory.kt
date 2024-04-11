package io.ticketaka.api.point.domain

import io.ticketaka.api.user.domain.User
import jakarta.persistence.*

@Entity
class PointHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val tsid: String,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @ManyToOne(targetEntity = User::class, optional = false, fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point,
) {
    enum class TransactionType {
        CHARGE, USE
    }
}