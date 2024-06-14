package io.ticketaka.api.user.infrastructure.persistence

import io.ticketaka.api.user.domain.token.RefreshTokenInfo
import io.ticketaka.api.user.domain.token.RefreshTokenInfoRepository
import io.ticketaka.api.user.infrastructure.jpa.JpaRefreshTokenInfoRepository
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenInfoRepositoryImpl(
    private val jpaRefreshTokenInfoRepository: JpaRefreshTokenInfoRepository,
) : RefreshTokenInfoRepository {
    override fun save(entity: RefreshTokenInfo): RefreshTokenInfo {
        return jpaRefreshTokenInfoRepository.save(entity)
    }

    override fun existsByRefreshTokenJti(jti: String): Boolean {
        return jpaRefreshTokenInfoRepository.findByRefreshTokenJti(jti) != null
    }

    override fun findByRefreshTokenJti(jti: String): RefreshTokenInfo? {
        return jpaRefreshTokenInfoRepository.findByRefreshTokenJti(jti)
    }
}
