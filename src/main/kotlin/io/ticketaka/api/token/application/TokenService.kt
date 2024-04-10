package io.ticketaka.api.token.application

import io.ticketaka.api.common.infrastructure.jwt.JwtProvider
import io.ticketaka.api.common.infrastructure.jwt.JwtTokens
import io.ticketaka.api.common.infrastructure.tsid.TsIdKeyGenerator
import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.token.domain.TokenRepository
import io.ticketaka.api.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class TokenService(
    private val jwtProvider: JwtProvider,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createToken(userTsId: String): JwtTokens {
        val user = userRepository.findByTsid(userTsId)
            ?: throw RuntimeException("사용자를 찾을 수 없습니다.")

        val token = Token.newInstance(user)
        val rawToken = jwtProvider.generate(user.tsid)
        tokenRepository.save(token)
        return rawToken
    }
}