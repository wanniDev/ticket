package io.ticketaka.api.user.infrastructure.jwt

import io.ticketaka.api.user.domain.Role
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.user.domain.exception.RefreshTokenNotFoundException
import io.ticketaka.api.user.domain.exception.UserNotFoundException
import io.ticketaka.api.user.domain.token.RefreshTokenInfoRepository
import io.ticketaka.api.user.domain.token.TokenExtractor
import io.ticketaka.api.user.domain.token.TokenGenerator
import io.ticketaka.api.user.domain.token.TokenParser
import io.ticketaka.api.user.domain.token.TokenReIssuer
import io.ticketaka.api.user.domain.token.Tokens
import org.springframework.stereotype.Component

@Component
class JwtTokenReIssuer(
    private val tokenGenerator: TokenGenerator,
    private val tokenExtractor: TokenExtractor,
    private val tokenParser: TokenParser,
    private val refreshTokenInfoRepository: RefreshTokenInfoRepository,
    private val userRepository: UserRepository,
) : TokenReIssuer {
    override fun reIssue(
        refreshToken: String,
        roles: Set<Role>,
    ): Tokens {
        val rawToken = tokenExtractor.extract(refreshToken)
        val token = tokenParser.parse(rawToken)

        token.verify(Scopes.REFRESH_TOKEN.authority())

        if (!refreshTokenInfoRepository.existsByRefreshTokenJti(token.jti!!)) {
            throw RefreshTokenNotFoundException()
        }

        val user = userRepository.findById(token.subject.toLong()) ?: throw UserNotFoundException()
        val refreshTokenJti = refreshTokenInfoRepository.findByRefreshTokenJti(token.jti) ?: throw RefreshTokenNotFoundException()
        refreshTokenJti.expire()
        return tokenGenerator.generate(user.getId().toString(), roles)
    }
}
