package io.ticketaka.api.token.application

import io.ticketaka.api.common.domain.map.TokenWaitingMap
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.point.domain.Point
import io.ticketaka.api.user.application.TokenUserCacheAsideQueryService
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class TokenUserServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val point = Point.newInstance()
        val user =
            User.newInstance(point.id)

        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doReturn user
            }
        val tokenUserService = TokenUserService(mock(), mockTokenUserCacheAsideQueryService, mock())

        // when
        val createToken = tokenUserService.createToken(user.id)

        // then
        assertThat(createToken).isNotNull
    }

    @Test
    fun `when user not found then throw exception`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingMap>()
        val mockTokenUserCacheAsideQueryService =
            mock<TokenUserCacheAsideQueryService> {
                on { getUser(any()) } doThrow NotFoundException("사용자를 찾을 수 없습니다.")
            }
        val tokenUserService = TokenUserService(mockTokenWaitingQueue, mockTokenUserCacheAsideQueryService, mock())

        // when
        assertThrows<NotFoundException> {
            tokenUserService.createToken(any())
        }

        // then
        verify(mockTokenUserCacheAsideQueryService).getUser(any())
    }

    @Test
    fun `peekToken returns true when the order of the token queue matches`() {
        // given
        val userId = 1L
        val tokenPosition0 = Token.newInstance(userId)
        val mockTokenWaitingMap =
            mock<TokenWaitingMap> {
                on { get(tokenPosition0.id) } doReturn tokenPosition0
            }
        val tokenUserService = TokenUserService(mockTokenWaitingMap, mock(), mock())

        // when
        tokenUserService.peekToken(tokenPosition0.id)
        // then
        verify(mockTokenWaitingMap).get(tokenPosition0.id)
    }
}
