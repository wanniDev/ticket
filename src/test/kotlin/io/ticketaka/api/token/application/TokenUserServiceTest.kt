package io.ticketaka.api.token.application

import io.ticketaka.api.common.domain.queue.TokenWaitingQueue
import io.ticketaka.api.common.exception.NotFoundException
import io.ticketaka.api.reservation.domain.point.Point
import io.ticketaka.api.user.application.TokenUserService
import io.ticketaka.api.user.domain.Token
import io.ticketaka.api.user.domain.User
import io.ticketaka.api.user.domain.UserRepository
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
import kotlin.test.assertFalse

@ExtendWith(MockitoExtension::class)
class TokenUserServiceTest {
    @Test
    fun createTokenTest() {
        // given
        val point = Point.newInstance()
        val user =
            User("userTsid1", point)
        user.id = 1

        val mockUserRepository =
            mock<UserRepository> {
                on { findByTsid(any()) } doReturn user
            }
        val tokenUserService = TokenUserService(mock(), mockUserRepository, mock())

        // when
        val createToken = tokenUserService.createToken("userTsid1")

        // then
        assertThat(createToken).isNotNull
    }

    @Test
    fun `when user not found then throw exception`() {
        // given
        val mockTokenWaitingQueue = mock<TokenWaitingQueue>()
        val mockUserRepository =
            mock<UserRepository> {
                on { findByTsid(any()) } doThrow NotFoundException("사용자를 찾을 수 없습니다.")
            }
        val tokenUserService = TokenUserService(mockTokenWaitingQueue, mockUserRepository, mock())

        // when
        assertThrows<NotFoundException> {
            tokenUserService.createToken("userTsid1")
        }

        // then
        verify(mockUserRepository).findByTsid("userTsid1")
    }

    @Test
    fun `peekToken returns true when the order of the token queue matches`() {
        // given
        val userId = 1L
        val tokenPosition0 = Token.newInstance(userId)
        val mockTokenRepository =
            mock<TokenWaitingQueue> {
                on { peek() } doReturn tokenPosition0
            }
        val tokenUserService = TokenUserService(mockTokenRepository, mock(), mock())

        // when
        val peekToken = tokenUserService.peekToken(tokenPosition0.tsid)
        // then
        verify(mockTokenRepository).peek()
        assert(peekToken)
    }

    @Test
    fun `peekToken returns false when the order of the token queue does not match`() {
        // given
        val userId1 = 1L
        val userId2 = 2L
        val tokenPosition0 = Token.newInstance(userId1)
        val tokenPosition1 = Token.newInstance(userId2)
        val tokenWaitingQueue =
            mock<TokenWaitingQueue> {
                on { peek() } doReturn tokenPosition0
            }
        val tokenUserService = TokenUserService(tokenWaitingQueue, mock(), mock())

        // when
        val peekToken = tokenUserService.peekToken(tokenPosition1.tsid)

        // then
        verify(tokenWaitingQueue).peek()
        assertFalse(peekToken)
    }
}
