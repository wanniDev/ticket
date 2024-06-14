package io.ticketaka.api.user.domain.token

interface RefreshTokenInfoRepository {
    fun save(entity: RefreshTokenInfo): RefreshTokenInfo

    fun existsByRefreshTokenJti(jti: String): Boolean

    fun findByRefreshTokenJti(jti: String): RefreshTokenInfo?
}
