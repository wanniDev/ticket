package io.ticketaka.api.token.application

import io.jsonwebtoken.impl.security.EdwardsCurve.findById
import io.ticketaka.api.common.infrastructure.jwt.JwtProvider
import io.ticketaka.api.common.infrastructure.jwt.JwtTokens
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.token.domain.Token
import io.ticketaka.api.token.domain.TokenRepository
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TokenServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val user =
            User("userTsid1", Point("pointTsid1", 0.0.toBigDecimal(), LocalDateTime.now(), LocalDateTime.now()))

        val mockJwtProvider = mock<JwtProvider> {
            on { generate(any())} doReturn JwtTokens("accessToken", "refreshToken")}

        val mockTokenRepository = mock<TokenRepository> {
            on { save(any()) } doReturn Token.newInstance(user)
        }

        val mockUserRepository = mock<UserRepository> {
            on { findByTsid(any()) } doReturn user
        }
        val tokenService = TokenService(mockJwtProvider, mockTokenRepository, mockUserRepository)

        // when
        tokenService.createToken("userTsid1")

        // then
        verify(mockJwtProvider).generate("userTsid1")
    }
}