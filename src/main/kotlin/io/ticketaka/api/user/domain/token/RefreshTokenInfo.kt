package io.ticketaka.api.user.domain.token

import io.ticketaka.api.user.domain.exception.AlreadyTokenExpiredException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token_info")
class RefreshTokenInfo protected constructor(
    @Id
    val id: Long,
    var refreshTokenJti: String? = null,
    var expired: Boolean = false,
) {
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        private set

    @PrePersist
    fun onPrePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun onPreUpdate() {
        updatedAt = LocalDateTime.now()
    }

    fun expire() {
        if (this.expired) {
            throw AlreadyTokenExpiredException()
        }
        this.expired = true
    }
}
