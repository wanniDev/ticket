package io.ticketaka.api.user.domain

import io.ticketaka.api.point.domain.Point
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    val tsid: String,
    @ManyToOne(targetEntity = Point::class, optional = false, fetch = FetchType.LAZY)
    val point: Point
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}