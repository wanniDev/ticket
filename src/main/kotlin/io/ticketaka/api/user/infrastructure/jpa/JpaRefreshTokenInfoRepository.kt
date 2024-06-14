package io.ticketaka.api.user.infrastructure.jpa

import io.ticketaka.api.user.domain.token.RefreshTokenInfo
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRefreshTokenInfoRepository : JpaRepository<RefreshTokenInfo, Long> {
    fun findByRefreshTokenJti(jti: String): RefreshTokenInfo?
}
