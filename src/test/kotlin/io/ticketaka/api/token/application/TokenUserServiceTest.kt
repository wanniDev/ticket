package io.ticketaka.api.token.application

import io.ticketaka.api.common.infrastructure.jwt.JwtProvider
import io.ticketaka.api.common.infrastructure.jwt.JwtTokens
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.domain.Token
import io.ticketaka.api.user.domain.domain.TokenRepository
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
import io.ticketaka.api.common.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class TokenUserServiceTest {
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
        val tokenUserService = TokenUserService(mockJwtProvider, mockTokenRepository, mockUserRepository)

        // when
        tokenUserService.createToken("userTsid1")

        // then
        verify(mockJwtProvider).generate("userTsid1")
    }

    @Test
    fun `when user not found then throw exception`() {
        // given
        val mockJwtProvider = mock<JwtProvider>()
        val mockTokenRepository = mock<TokenRepository>()
        val mockUserRepository = mock<UserRepository> {
            on { findByTsid(any()) } doThrow NotFoundException("사용자를 찾을 수 없습니다.")
        }
        val tokenUserService = TokenUserService(mockJwtProvider, mockTokenRepository, mockUserRepository)

        // when
        assertThrows<NotFoundException> {
            tokenUserService.createToken("userTsid1")
        }

        // then
        verify(mockUserRepository).findByTsid("userTsid1")
    }
}