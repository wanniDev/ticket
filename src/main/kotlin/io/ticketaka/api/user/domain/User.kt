package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.point.domain.Point
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User protected constructor(
    @Id
    val id: Long,
    var pointId: Long,
    val email: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = hashSetOf(Role.USER),
) : Persistable<Long> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    override fun getId(): Long = id

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    companion object {
        fun newInstance(pointId: Long): User =
            User(
                id = TsIdKeyGenerator.nextLong(),
                email = "",
                pointId = pointId,
            )

        fun newInstance(email: String): User =
            User(
                id = TsIdKeyGenerator.nextLong(),
                email = email,
                pointId = Point.newInstance().getId(),
            )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (pointId != other.pointId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + pointId.hashCode()
        return result
    }
}
