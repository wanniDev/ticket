package io.ticketaka.api.user.domain

import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User protected constructor(
    @Id
    val id: Long,
    var pointId: Long,
) {
    companion object {
        fun newInstance(pointId: Long): User {
            return User(
                id = TsIdKeyGenerator.nextLong(),
                pointId = pointId,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
