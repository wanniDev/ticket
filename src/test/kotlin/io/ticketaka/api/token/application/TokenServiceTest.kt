package io.ticketaka.api.token.application

import io.ticketaka.api.common.infrastructure.jwt.JwtProvider
import io.ticketaka.api.common.infrastructure.jwt.JwtTokens
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.application.TokenService
import io.ticketaka.api.user.domain.domain.Token
import io.ticketaka.api.user.domain.domain.TokenRepository
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class TokenServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val point = Point.newInstance()
        val user =
            User("userTsid1", point)

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